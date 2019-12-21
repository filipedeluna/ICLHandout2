package compiler.frame;

import compiler.CompilerType;
import compiler.errors.CompileError;
import node.ASTNode;

import java.util.ArrayList;

public final class Frame {
  private String frameId;
  private Frame parentFrame;
  private ArrayList<FrameField> fields;

  public Frame(String frameId) {
    this.frameId = frameId;
    fields = new ArrayList<>();
  }

  public Frame(String frameId, Frame parentFrame) {
    this.frameId = frameId;
    this.parentFrame = parentFrame;
    fields = new ArrayList<>();
  }

  public void addField(String fieldId, CompilerType type) throws CompileError {
    if (findField(fieldId) != null)
      throw new CompileError("Duplicate field " + fieldId, "add field to frame");

    if (type.isLit()) {
      fields.add(new FrameField(fieldId, type));
      return;
    }

    if (type == CompilerType.STRUCT) {
      fields.add(new FrameStructField(fieldId));
      return;
    }
    throw new CompileError("Invalid type, literal or struct expected from variable " + fieldId, "add field to frame");
  }

  public void addFunField(String fieldId, ASTNode node, ArrayList<CompilerType> paramTypes, CompilerType returnType) throws CompileError {
    if (findField(fieldId) != null)
      throw new CompileError("Duplicate field " + fieldId, "add fun field to frame");

    fields.add(new FrameFunctionField(fieldId, node, paramTypes, returnType));
  }

  public void addFieldToStructField(String structId, String fieldId, CompilerType type) throws CompileError {
    FrameField structField = findField(structId);

    if (structField == null)
      throw new CompileError("Variable " + fieldId + " not defined in any frame", "add field to struct");

    if (!(structField instanceof FrameStructField))
      throw new CompileError("Variable " + fieldId + " is not a struct", "add field to struct");

    if (((FrameStructField) structField).hasField(fieldId))
      throw new CompileError("Duplicate field", "add field to struct");

    ((FrameStructField) structField).addStructField(fieldId, type);
  }

  // Obtain a variable's respective frame field id and the list of sub-frames to get to it
  public FrameField getFrameField(String fieldId) throws CompileError {
    ArrayList<String> subFrameList = new ArrayList<>();

    Frame exploredFrame = this;
    FrameField field;

    while (exploredFrame != null) {
      subFrameList.add(exploredFrame.frameId);

      field = exploredFrame.findField(fieldId);

      if (field != null)
        return field;

      exploredFrame = exploredFrame.parentFrame;
    }

    throw new CompileError("Variable " + fieldId + " not defined in any frame", "Get frame field");
  }

  public ArrayList<FrameField> getFields() {
    return fields;
  }

  public boolean hasField(String id) {
    return findField(id) != null;
  }

  public String getFrameId() {
    return frameId;
  }

  public Frame getParentFrame() {
    return parentFrame;
  }

  public boolean hasParent() {
    return parentFrame != null;
  }

  /*
    UTILS
  */
  private FrameField findField(String fieldId) {
    for (FrameField field : fields) {
      if (field.getFieldId().equals(fieldId))
        return field;
    }

    return null;
  }
}

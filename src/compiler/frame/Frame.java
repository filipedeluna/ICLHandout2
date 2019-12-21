package compiler.frame;

import compiler.CompilerType;
import compiler.errors.CompileError;
import node.ASTNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public final class Frame {
  private String frameId;
  private Frame parentFrame;
  private ArrayList<FrameField> fields;
  private boolean isFunction = false;

  public Frame(String frameId) {
    this.frameId = frameId;
    fields = new ArrayList<>();
  }

  public Frame(String frameId, Frame parentFrame) {
    this(frameId);
    this.parentFrame = parentFrame;
  }

  public Frame(String frameId, Frame parentFrame, boolean isFunction) {
    this(frameId, parentFrame);
    this.isFunction = isFunction;
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

  public void addFunField(String fieldId, ASTNode node, LinkedHashMap<String, CompilerType> params, CompilerType returnType) throws CompileError {
    if (findField(fieldId) != null)
      throw new CompileError("Duplicate field " + fieldId, "add fun field to frame");

    fields.add(new FrameFunctionField(fieldId, node, params, returnType));
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

      if (field != null) {
        field.setFrameList(subFrameList);
        return field;
      }

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

  public boolean isFunction() {
    return isFunction;
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

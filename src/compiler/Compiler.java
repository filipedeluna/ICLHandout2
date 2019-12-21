package compiler;

import compiler.errors.*;
import compiler.frame.Frame;
import compiler.frame.FrameField;
import compiler.frame.FrameStructField;
import compiler.writer.CompilerWriterHandler;
import node.ASTNode;
import values.IValue;
import values.VFun;
import values.VStruct;

import java.util.ArrayList;

public final class Compiler {
  private CompilerWriterHandler compilerWriterHandler;
  private Frame currentFrame;
  private int frameIdCounter;
  private IValue tempValue;

  public Cache cache;

  public Compiler() throws CompileError {
    this.compilerWriterHandler = new CompilerWriterHandler(this);
    this.frameIdCounter = 0;

    currentFrame = null;
    cache = new Cache();
  }

  public void emit(ByteCode byteCode, String params) throws CompileError {
    compilerWriterHandler.write(byteCode, params);
  }

  public void emit(ByteCode byteCode) throws CompileError {
    compilerWriterHandler.write(byteCode);
  }

  public void beginFrame() throws CompileError {
    String frameId = generateFrameId();

    if (currentFrame == null)
      currentFrame = new Frame(frameId);
    else
      currentFrame = new Frame(frameId, currentFrame);

    compilerWriterHandler.beginFrame(currentFrame);
  }

  public void endFrame() throws CompileError {
    compilerWriterHandler.endFrame(currentFrame);
    currentFrame = currentFrame.getParentFrame();
  }

  // For Structs and Function types
  public void pushTempValue(IValue value) throws CompileError {
    if (tempValue != null)
      throw new CompileError("Register is not empty", "push temp value for initialization");

    if (!(value instanceof VFun) && !(value instanceof VStruct))
      throw new CompileError("Invalid value type", "push temp value for initialization");

    tempValue = value;
  }

  public IValue popTempValue() throws CompileError {
    if (tempValue == null)
      throw new CompileError("Register is empty", "pop temp value for initialization");

    IValue value = tempValue;
    tempValue = null;

    return value;
  }

  public IValue peekTempValue() throws CompileError {
    if (tempValue == null)
      throw new CompileError("Register is empty", "peak temp value");

    return tempValue;
  }

  public boolean hasTempValue() {
    return tempValue != null;
  }

  public void addFrameField(String id, CompilerType type) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + id, "add frame field");

    if (currentFrame.hasField(id))
      throw new CompileError("Duplicate variable " + id, "add frame field");

    currentFrame.addField(id, type);
  }

  public void addFrameFunctionField(String id, ASTNode node, ArrayList<CompilerType> paramTypes, CompilerType returnType) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + id, "add frame fun field");

    if (currentFrame.hasField(id))
      throw new CompileError("Duplicate variable " + id, "add frame fun field");

    currentFrame.addFunField(id, node, paramTypes, returnType);
  }

  public void addFieldToFrameStructField(String structId, String fieldId, CompilerType type) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + structId, "add frame struct frame field");

    if (currentFrame.hasField(structId))
      throw new CompileError("Duplicate variable " + structId, "add frame struct frame field");

    currentFrame.addFieldToStructField(structId, fieldId, type);
  }

  public void updateFrameField(String id, ASTNode value) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + id, "update frame field");

    FrameField frameField = currentFrame.getFrameField(id);

    compilerWriterHandler.getFrameParentFields(frameField);

    value.compile(this);

    compilerWriterHandler.putFrameField(frameField);
  }

  public void updateFrameStructField(String structId, String fieldId, ASTNode value) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + structId, "update frame struct field");

    FrameField frameField = currentFrame.getFrameField(structId);

    if (!(frameField instanceof FrameStructField))
      throw new CompileError("Trying to update a struct field on a non-struct value with id " + structId, "update frame struct field");

    compilerWriterHandler.getFrameParentFields(frameField);

    value.compile(this);

    compilerWriterHandler.putFrameStructField((FrameStructField) frameField, fieldId);
  }

  public void getFrameField(String fieldId) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + fieldId, "get frame field");

    FrameField frameField = currentFrame.getFrameField(fieldId);

    compilerWriterHandler.getFrameField(frameField);
  }

  public CompilerType getFrameFieldType(String fieldId) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + fieldId, "get frame field type");

    FrameField frameField = currentFrame.getFrameField(fieldId);

    return frameField.getType();
  }

  public void getFrameStructField(String structId, String fieldId) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + fieldId, "get frame struct field");

    FrameField frameField = currentFrame.getFrameField(structId);

    if (!(frameField instanceof FrameStructField))
      throw new CompileError("Variable is not a struct " + fieldId, "get frame struct field");

    compilerWriterHandler.getFrameStructField((FrameStructField) frameField, fieldId);
  }

  public void compare(ByteCode comparisonByteCode) throws CompileError {
    compilerWriterHandler.compare(comparisonByteCode);
  }

  public int countLines(ASTNode action) throws CompileError {
    compilerWriterHandler.startLineCounter();
    action.compile(this);
    return compilerWriterHandler.endLineCounter();
  }

  public int getCurrentLine() {
    return compilerWriterHandler.getCurrentLine();
  }

  public String getCurrentLineOffset(int offset) {
    return compilerWriterHandler.getCurrentLineOffset(offset);
  }


  public void loadStaticLink() throws CompileError {
    compilerWriterHandler.loadStaticLink();
  }

  public void end() throws CompileError {
    compilerWriterHandler.close();
  }

  public void deleteGeneratedFiles() throws CompileError {
    compilerWriterHandler.deleteGeneratedFiles();
  }

  public void startPrint() throws CompileError {
    compilerWriterHandler.startPrint();
  }

  public void endPrint(CompilerType type) throws CompileError {
    compilerWriterHandler.endPrint(type);
  }

  /*
    UTILS
  */
  private String generateFrameId() {
    return "f" + frameIdCounter++;
  }
}

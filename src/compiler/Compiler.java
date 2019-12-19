package compiler;

import compiler.errors.*;
import node.ASTNode;
import values.IValue;
import values.VFun;
import values.VStruct;

public final class Compiler {
  private CompilerWriterHandler compilerWriterHandler;
  private Frame currentFrame;
  private int frameIdCounter;
  private IValue tempValue;


  // Temporary frame field "memory" register
  private String currentFieldId;

  public Compiler() throws CompileError {
    this.compilerWriterHandler = new CompilerWriterHandler();
    this.frameIdCounter = 0;

    currentFrame = null;
    currentFieldId = null;
    tempValue = null;
  }

  public void emit(ByteCode byteCode, String params) throws CompileError {
    compilerWriterHandler.write(byteCode, params);
  }

  public void emit(ByteCode byteCode) throws CompileError {
    compilerWriterHandler.write(byteCode);
  }

  public void beginFrame() throws CompileError {
    String frameId = generateFrameId();

    if (currentFrame == null) {
      //currentFrame = new Frame(frameId);
      compilerWriterHandler.beginFrame(currentFrame);
    } else {
      currentFrame = new Frame(frameId, currentFrame);
      compilerWriterHandler.beginSubFrame(currentFrame);
    }
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

  public IValue peakTempValue() throws CompileError {
    if (tempValue == null)
      throw new CompileError("Register is empty", "peak temp value");

    return tempValue;
  }

  public boolean hasTempValue() {
    return tempValue != null;
  }

  public void pushFrameField(String id) throws CompileError {
    if (currentFrame.getFrameField(id) == null)
      throw new CompileError("Undefined variable " + id, "push frame field");

    currentFieldId = id;
  }

  public String popFrameField() throws CompileError {
    if (currentFieldId == null)
      throw new CompileError("Compiler temporary register is empty", "pop frame field");

    String value = currentFieldId;
    currentFieldId = null;

    return value;
  }

  public void addFrameField(String id, String type) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + id, "add frame field");

    if (currentFrame.hasField(id))
      throw new CompileError("Duplicate variable " + id, "add frame field");

    //String varIndex = currentFrame.addField(id);
   // compilerWriterHandler.addFieldToFrameClassFile(currentFrame.getFrameId(), varIndex, type);
    //compilerWriterHandler.addFrameField(varIndex, currentFrame);
  }

  public void updateFrameField(String id, ASTNode value) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + id, "update frame field");

    FrameField frameField = currentFrame.getFrameField(id);

    if (frameField == null)
      throw new CompileError("Undefined variable " + id, "update frame field");

    compilerWriterHandler.getFrameParentFields(frameField);

    value.compile(this);

    compilerWriterHandler.updateFrameField(frameField);
  }

  public void getFrameField(String id) throws CompileError {
    if (currentFrame == null)
      throw new CompileError("Variable referencing outside a frame " + id, "get frame field");

    FrameField frameField = currentFrame.getFrameField(id);

    if (frameField == null)
      throw new CompileError("Undefined variable " + id, "get frame field");

    compilerWriterHandler.getFrameField(frameField);
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

  public void endFrame() throws CompileError {
    compilerWriterHandler.endFrame(currentFrame);
    currentFrame = currentFrame.getParentFrame();
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

  /*
    UTILS
  */
  private String generateFrameId() {
    return "f" + frameIdCounter++;
  }
}

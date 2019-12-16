package compiler;

import compiler.errors.*;
import node.ASTNode;

public final class Compiler {
  private CompilerWriterHandler compilerWriterHandler;
  private Frame currentFrame;
  private int frameIdCounter;

  // Temporary frame field "memory" register
  private String currentFieldId;

  public Compiler() throws CompilerError {
    this.compilerWriterHandler = new CompilerWriterHandler();
    this.frameIdCounter = 0;

    currentFrame = null;

    currentFieldId = null;
  }

  public void emit(ByteCode byteCode, String params) throws CompilerError {
    compilerWriterHandler.write(byteCode, params);
  }

  public void emit(ByteCode byteCode) throws CompilerError {
    compilerWriterHandler.write(byteCode);
  }

  public void beginFrame() throws CompilerError {
    String frameId = generateFrameId();

    if (currentFrame == null) {
      currentFrame = new Frame(frameId);
      compilerWriterHandler.beginFrame(currentFrame);
    } else {
      currentFrame = new Frame(frameId, currentFrame);
      compilerWriterHandler.beginSubFrame(currentFrame);
    }
  }

  public void pushFrameField(String id) throws CompilerError {
    if (currentFrame.getFrameField(id) == null)
      throw new CompilerUndefinedVariableError(id);

    currentFieldId = id;
  }

  public String popFrameField() throws CompilerError {
    if (currentFieldId == null)
      throw new EmptyCompilerRegisterError();

    String value = currentFieldId;
    currentFieldId = null;

    return value;
  }

  public void addFrameField(String id) throws CompilerError {
    if (currentFrame == null)
      throw new VariableReferencingError(id);

    if (currentFrame.hasField(id))
      throw new CompilerDuplicateVariableError(id);

    String varIndex = currentFrame.addField(id);
    compilerWriterHandler.addFrameField(varIndex, currentFrame);
  }

  public void updateFrameField(String id, ASTNode value) throws CompilerError {

    if (currentFrame == null)
      throw new VariableReferencingError(id);

    FrameField frameField = currentFrame.getFrameField(id);

    if (frameField == null)
      throw new CompilerUndefinedVariableError(id);

    compilerWriterHandler.getFrameParentFields(frameField);

    value.compile(this);

    compilerWriterHandler.updateFrameField(frameField);
  }

  public void getFrameField(String id) throws CompilerError {
    if (currentFrame == null)
      throw new VariableReferencingError(id);

    FrameField frameField = currentFrame.getFrameField(id);

    if (frameField == null)
      throw new CompilerUndefinedVariableError(id);

    compilerWriterHandler.getFrameField(frameField);
  }

  public void compare(ByteCode comparisonByteCode) throws OutputFileWriteError {
    compilerWriterHandler.compare(comparisonByteCode);
  }

  public int countLines(ASTNode action) throws CompilerError {
    compilerWriterHandler.startLineCounter();
    action.compile(this);
    return compilerWriterHandler.endLineCounter();
  }

  public int getCurrentLine() {
    return compilerWriterHandler.getCurrentLine();
  }

  public void endFrame() throws CompilerError {
    compilerWriterHandler.endFrame(currentFrame);
    currentFrame = currentFrame.getParentFrame();
  }

  public void loadStaticLink() throws CompilerError {
    compilerWriterHandler.loadStaticLink();
  }

  public void end() throws OutputFileWriteError {
    compilerWriterHandler.close();
  }

  public void deleteGeneratedFiles() throws FailedToDeleteFilesError {
    compilerWriterHandler.deleteGeneratedFiles();
  }

  /*
    UTILS
  */
  private String generateFrameId() {
    return "f" + frameIdCounter++;
  }
}

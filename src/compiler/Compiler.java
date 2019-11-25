package compiler;

import errors.compiler.*;
import node.ASTNode;

public final class Compiler {
  private CompilerWriterHandler compilerWriterHandler;
  private Frame currentFrame;
  private int frameIdCounter;

  // Temporary frame field "memory" register
  private String currentFieldId;

  public Compiler() throws CompilerException {
    this.compilerWriterHandler = new CompilerWriterHandler();
    this.frameIdCounter = 0;

    currentFrame = null;

    currentFieldId = null;
  }

  public void emit(ByteCode byteCode, String params) throws CompilerException {
    compilerWriterHandler.write(byteCode, params);
  }

  public void emit(ByteCode byteCode) throws CompilerException {
    compilerWriterHandler.write(byteCode);
  }

  public void beginFrame() throws CompilerException {
    String frameId = generateFrameId();

    if (currentFrame == null) {
      currentFrame = new Frame(frameId);
      compilerWriterHandler.beginFrame(currentFrame);
    } else {
      currentFrame = new Frame(frameId, currentFrame);
      compilerWriterHandler.beginSubFrame(currentFrame);
    }
  }

  public void pushFrameField(String id) throws CompilerException {
    if (currentFrame.getFrameField(id) == null)
      throw new CompilerUndefinedVariableException(id);

    currentFieldId = id;
  }

  public String popFrameField() throws CompilerException {
    if (currentFieldId == null)
      throw new EmptyCompilerRegisterException();

    String value = currentFieldId;
    currentFieldId = null;

    return value;
  }

  public void addFrameField(String id) throws CompilerException {
    if (currentFrame == null)
      throw new VariableReferencingException(id);

    if (currentFrame.hasField(id))
      throw new CompilerDuplicateVariableException(id);

    String varIndex = currentFrame.addField(id);
    compilerWriterHandler.addFrameField(varIndex, currentFrame);
  }

  public void updateFrameField(String id, ASTNode value) throws CompilerException {

    if (currentFrame == null)
      throw new VariableReferencingException(id);

    FrameField frameField = currentFrame.getFrameField(id);

    if (frameField == null)
      throw new CompilerUndefinedVariableException(id);

    compilerWriterHandler.getFrameParentFields(frameField);

    value.compile(this);

    compilerWriterHandler.updateFrameField(frameField);
  }

  public void getFrameField(String id) throws CompilerException {
    if (currentFrame == null)
      throw new VariableReferencingException(id);

    FrameField frameField = currentFrame.getFrameField(id);

    if (frameField == null)
      throw new CompilerUndefinedVariableException(id);

    compilerWriterHandler.getFrameField(frameField);
  }

  public void compare(ByteCode comparisonByteCode) throws OutputFileWriteException {
    compilerWriterHandler.compare(comparisonByteCode);
  }

  public int countLines(ASTNode action) throws CompilerException {
    compilerWriterHandler.startLineCounter();
    action.compile(this);
    return compilerWriterHandler.endLineCounter();
  }

  public int getCurrentLine() {
    return compilerWriterHandler.getCurrentLine();
  }

  public void endFrame() throws CompilerException {
    compilerWriterHandler.endFrame(currentFrame);
    currentFrame = currentFrame.getParentFrame();
  }

  public void loadStaticLink() throws CompilerException {
    compilerWriterHandler.loadStaticLink();
  }

  public void end() throws OutputFileWriteException {
    compilerWriterHandler.close();
  }

  public void deleteGeneratedFiles() throws FailedToDeleteFilesException {
    compilerWriterHandler.deleteGeneratedFiles();
  }

  /*
    UTILS
  */
  private String generateFrameId() {
    return "f" + frameIdCounter++;
  }
}

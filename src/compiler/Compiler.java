package compiler;

import errors.compiler.*;
import node.ASTNode;

public final class Compiler {
  private Writer writer;
  private Frame currentFrame;
  private int frameIdCounter;

  // Temporary frame field "memory" register
  private String currentFieldId;

  public Compiler() throws CompilerException {
    this.writer = new Writer();
    this.frameIdCounter = 0;

    currentFrame = null;

    currentFieldId = null;
  }

  public void emit(ByteCode byteCode, String params) throws CompilerException {
    writer.write(byteCode, params);
  }

  public void emit(ByteCode byteCode) throws CompilerException {
    writer.write(byteCode);
  }

  public void beginFrame() throws CompilerException {
    String frameId = generateFrameId();

    if (currentFrame == null) {
      currentFrame = new Frame(frameId);
      writer.beginFrame(currentFrame);
    } else {
      currentFrame = new Frame(frameId, currentFrame);
      writer.beginSubFrame(currentFrame);
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
    writer.addFrameField(varIndex, currentFrame);
  }

  public void updateFrameField(String id, ASTNode value) throws CompilerException {

    if (currentFrame == null)
      throw new VariableReferencingException(id);

    FrameField frameField = currentFrame.getFrameField(id);

    if (frameField == null)
      throw new CompilerUndefinedVariableException(id);

    writer.getFrameParentFields(frameField);

    value.compile(this);

    writer.updateFrameField(frameField);
  }

  public void getFrameField(String id) throws CompilerException {
    if (currentFrame == null)
      throw new VariableReferencingException(id);

    FrameField frameField = currentFrame.getFrameField(id);

    if (frameField == null)
      throw new CompilerUndefinedVariableException(id);

    writer.getFrameField(frameField);
  }

  public void endFrame() throws CompilerException {
    writer.endFrame(currentFrame);
    currentFrame = currentFrame.getParentFrame();
  }

  public void loadStaticLink() throws CompilerException {
    writer.loadStaticLink();
  }

  public void end() throws OutputFileWriteException {
    writer.close();
  }

  public void deleteGeneratedFiles() throws FailedToDeleteFilesException {
    writer.deleteFiles();
  }

  /*
    UTILS
  */
  private String generateFrameId() {
    return "f" + frameIdCounter++;
  }
}

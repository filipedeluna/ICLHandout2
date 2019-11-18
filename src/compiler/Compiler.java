package compiler;

import errors.compiler.CompilerException;
import errors.compiler.FailedToDeleteFilesException;
import errors.compiler.OutputFileWriteException;
import errors.compiler.VariableReferencingException;

public final class Compiler {
  private CompilerWriter compilerWriter;
  private int frameIdCounter;
  private CompilerFrame currentFrame;

  public Compiler() throws CompilerException {
    this.compilerWriter = new CompilerWriter();
    this.frameIdCounter = 0;

    currentFrame = null;
  }

  public void emit(ByteCode byteCode, String params) throws CompilerException {
    compilerWriter.write(byteCode, params);
  }

  public void emit(ByteCode byteCode) throws CompilerException {
    compilerWriter.write(byteCode);
  }

  public void beginFrame() throws CompilerException {
    String frameId = generateFrameId();

    if (currentFrame == null) {
      currentFrame = new CompilerFrame(frameId);
      compilerWriter.beginFrame(currentFrame);
    } else {
      currentFrame = new CompilerFrame(frameId, currentFrame);
      compilerWriter.beginSubFrame(currentFrame);
    }
  }

  public void addFieldToFrame(String id) throws CompilerException {
    if (currentFrame == null)
      throw new VariableReferencingException(id);

    String varIndex = currentFrame.addField(id);
    compilerWriter.addFieldToFrame(varIndex, currentFrame);
  }

  public void getFieldFromFrame(String id) throws CompilerException {
    if (currentFrame == null)
      throw new VariableReferencingException(id);

    CompilerFrameField compilerFrameField = currentFrame.getFrameField(id);

    compilerWriter.getFieldFromFrame(compilerFrameField);
  }

  public void endFrame() throws CompilerException {
    compilerWriter.endFrame(currentFrame);
    currentFrame = currentFrame.getParentFrame();
  }

  public void loadStaticLink() throws CompilerException {
    compilerWriter.loadStaticLink();
  }

  public void end() throws OutputFileWriteException {
    compilerWriter.close();
  }

  public void deleteGeneratedFiles() throws FailedToDeleteFilesException {
    compilerWriter.deleteFiles();
  }

  /*
    UTILS
  */
  private String generateFrameId() {
    return "f" + frameIdCounter++;
  }
}

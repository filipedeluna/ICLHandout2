package compiler.errors;

public class FileAlreadyExistsError extends CompilerError {
  public FileAlreadyExistsError(String frameId) {
    super("File " + frameId + "already exists.");
  }
}
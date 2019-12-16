package compiler.errors;

public class FailedToDeleteFilesError extends CompilerError {
  public FailedToDeleteFilesError() {
    super("Failed to delete leftover files.");
  }
}
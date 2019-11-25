package errors.compiler;

public class OutputFileWriteException extends CompilerException {
  public OutputFileWriteException(String file) {
    super("Failed to access output file \"" + file + "\".");
  }
}

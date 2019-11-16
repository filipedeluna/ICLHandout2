package errors.compiler;

public class OutputFileWriteException extends CompilerException {
  public OutputFileWriteException(String file) {
    super("Failed to write to output file \"" + file + "\".");
  }
}

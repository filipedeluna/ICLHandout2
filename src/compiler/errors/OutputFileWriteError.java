package compiler.errors;

public class OutputFileWriteError extends CompilerError {
  public OutputFileWriteError(String file) {
    super("Failed to access output file \"" + file + "\".");
  }
}

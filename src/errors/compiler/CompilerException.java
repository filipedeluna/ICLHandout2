package errors.compiler;

public abstract class CompilerException extends Exception {
  public CompilerException(String text) {
    super("Compiler Error: " + text);
  }
}

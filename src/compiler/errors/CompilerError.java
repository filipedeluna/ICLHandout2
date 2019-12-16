package compiler.errors;

public abstract class CompilerError extends Exception {
  public CompilerError(String text) {
    super("Compiler Error: " + text);
  }
}

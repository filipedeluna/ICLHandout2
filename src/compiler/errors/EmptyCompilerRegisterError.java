package compiler.errors;

public class EmptyCompilerRegisterError extends CompilerError {
  public EmptyCompilerRegisterError() {
    super("No variable found in temporary compiler register.");
  }
}
package compiler.errors;

public class CompilerDuplicateVariableError extends CompilerError {
  public CompilerDuplicateVariableError(String id) {
    super("Variable " + id + "already declared in frame.");
  }
}

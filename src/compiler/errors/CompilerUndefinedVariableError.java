package compiler.errors;

public class CompilerUndefinedVariableError extends CompilerError {
  public CompilerUndefinedVariableError(String id) {
    super("Variable " + id + " not defined.");
  }
}

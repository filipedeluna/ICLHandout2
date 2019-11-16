package errors.compiler;

public class CompilerUndefinedVariableException extends CompilerException {
  public CompilerUndefinedVariableException(String id) {
    super("Variable " + id + " not defined.");
  }
}

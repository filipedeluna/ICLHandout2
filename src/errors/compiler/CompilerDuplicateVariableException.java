package errors.compiler;

public class CompilerDuplicateVariableException extends CompilerException {
  public CompilerDuplicateVariableException(String id) {
    super("Variable " + id + "already declared in frame.");
  }
}

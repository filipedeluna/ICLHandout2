package errors.compiler;

public class VariableReferencingException extends CompilerException {
  public VariableReferencingException(String varId) {
    super("Referenced or created variable " + varId + " out of scope.");
  }
}

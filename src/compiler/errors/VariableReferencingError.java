package compiler.errors;

public class VariableReferencingError extends CompilerError {
  public VariableReferencingError(String varId) {
    super("Referenced or created variable " + varId + " out of scope.");
  }
}

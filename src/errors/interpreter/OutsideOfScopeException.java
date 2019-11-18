package errors.interpreter;

public class OutsideOfScopeException extends InterpreterException {
  public OutsideOfScopeException(String var) {
    super("Attempted to associate variable " + var + " outside of scope.");
  }
}

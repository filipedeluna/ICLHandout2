package interpreter.errors;

public class OutsideOfScopeError extends InterpreterError {
  public OutsideOfScopeError(String var) {
    super("Attempted to associate variable " + var + " outside of scope.");
  }
}

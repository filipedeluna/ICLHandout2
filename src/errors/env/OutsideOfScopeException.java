package errors.env;

public class OutsideOfScopeException extends EnvironmentException {
  public OutsideOfScopeException(String var) {
    super("Attempted to associate variable " + var + " outside of scope.");
  }
}

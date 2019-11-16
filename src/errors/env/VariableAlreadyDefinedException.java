package errors.env;

public class VariableAlreadyDefinedException extends EnvironmentException {
  public VariableAlreadyDefinedException(String var) {
    super("Variable " + var + "is already defined.");
  }
}

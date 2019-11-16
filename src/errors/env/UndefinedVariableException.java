package errors.env;

public class UndefinedVariableException extends EnvironmentException {
  public UndefinedVariableException(String var) {
    super("Variable " + var + "is not defined.");
  }
}

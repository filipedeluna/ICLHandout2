package errors.interpreter;

public class UndefinedVariableException extends InterpreterException {
  public UndefinedVariableException(String var) {
    super("Variable " + var + "is not defined.");
  }
}

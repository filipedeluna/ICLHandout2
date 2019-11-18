package errors.interpreter;

public class VariableAlreadyDefinedException extends InterpreterException {
  public VariableAlreadyDefinedException(String var) {
    super("Variable " + var + "is already defined.");
  }
}

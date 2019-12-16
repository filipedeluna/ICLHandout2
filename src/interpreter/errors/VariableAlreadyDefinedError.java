package interpreter.errors;

public class VariableAlreadyDefinedError extends InterpreterError {
  public VariableAlreadyDefinedError(String var) {
    super("Variable " + var + "is already defined.");
  }
}

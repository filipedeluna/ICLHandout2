package interpreter.errors;

public class InterpreterUndefinedVariableError extends InterpreterError {
  public InterpreterUndefinedVariableError(String var) {
    super("Variable " + var + "is not defined.");
  }
}

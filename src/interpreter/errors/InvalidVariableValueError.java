package interpreter.errors;

import values.IValue;

public class InvalidVariableValueError extends InterpreterError {
  public InvalidVariableValueError(IValue value) {
    super("Cannot create variable of type " + value.type());
  }
}

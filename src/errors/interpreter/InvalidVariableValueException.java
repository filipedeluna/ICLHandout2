package errors.interpreter;

import value.IValue;

public class InvalidVariableValueException extends InterpreterException {
  public InvalidVariableValueException(IValue value) {
    super("Cannot create variable of type " + value.typeToString());
  }
}

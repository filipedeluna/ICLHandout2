package errors.interpreter;

import value.IValue;

public class InvalidConstantValueException extends InterpreterException {
  public InvalidConstantValueException(IValue value) {
    super("Cannot create constant of type " + value.typeToString());
  }
}

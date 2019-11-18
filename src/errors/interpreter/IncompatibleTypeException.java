package errors.interpreter;

import value.IValue;

public class IncompatibleTypeException extends InterpreterException {
  public IncompatibleTypeException(String op, IValue val) {
    super("Incompatible operation " + op + " on type " + val + ".");
  }
}

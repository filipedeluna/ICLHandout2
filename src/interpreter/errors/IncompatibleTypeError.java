package interpreter.errors;

import values.IValue;

public class IncompatibleTypeError extends InterpreterError {
  public IncompatibleTypeError(String op, IValue val) {
    super("Incompatible operation " + op + " on type " + val + ".");
  }
}

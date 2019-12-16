package interpreter.errors;

import values.IValue;

public class IncompatibleTypesError extends InterpreterError {
  public IncompatibleTypesError(String op, IValue v1, IValue v2) {
    super("Incompatible operation " + op + " on types " + v1.type() + " " + v2.type() + ".");
  }
}

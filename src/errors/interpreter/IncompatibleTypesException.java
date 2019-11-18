package errors.interpreter;

import value.IValue;

public class IncompatibleTypesException extends InterpreterException {
  public IncompatibleTypesException(String op, IValue v1, IValue v2) {
    super("Incompatible operation " + op + " on types " + v1.typeToString() + " " + v2.typeToString() + ".");
  }
}

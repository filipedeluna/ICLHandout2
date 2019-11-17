package errors.eval;

import value.IValue;

public class IncompatibleTypeException extends EvaluationException {
  public IncompatibleTypeException(String op, IValue val) {
    super("Incompatible operation " + op + " on type " + val + ".");
  }
}

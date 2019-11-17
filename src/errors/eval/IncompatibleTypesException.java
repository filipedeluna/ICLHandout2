package errors.eval;

import value.IValue;

public class IncompatibleTypesException extends EvaluationException {
  public IncompatibleTypesException(String op, IValue v1, IValue v2) {
    super("Incompatible operation " + op + " on types " + v1.type() + " " + v2.type() + ".");
  }
}

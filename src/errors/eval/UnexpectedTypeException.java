package errors.eval;

public class UnexpectedTypeException extends EvaluationException {
  public UnexpectedTypeException(String type1, String type2) {
    super("Unexpected value of type " + type1 + " expected type " + type2 + ";");
  }
}

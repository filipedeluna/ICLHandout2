package errors.eval;

public class DivideByZeroException extends EvaluationException {
  public DivideByZeroException() {
    super("Cannot divide by zero.");
  }
}

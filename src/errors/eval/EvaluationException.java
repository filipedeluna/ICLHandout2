package errors.eval;

public abstract class EvaluationException extends Exception {
  public EvaluationException(String text) {
    super("Environment Error: " + text);
  }
}


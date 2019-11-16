package errors.env;

public abstract class EnvironmentException extends Exception {
  public EnvironmentException(String text) {
    super("Environment Error: " + text);
  }
}

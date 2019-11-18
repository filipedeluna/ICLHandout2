package errors.interpreter;

public class ValueNotFoundInMemoryException extends InterpreterException {
  public ValueNotFoundInMemoryException() {
    super("Could not find value in memory.");
  }
}

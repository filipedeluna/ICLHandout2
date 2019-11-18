package errors.interpreter;

public class DivideByZeroException extends InterpreterException {
  public DivideByZeroException() {
    super("Cannot divide by zero.");
  }
}

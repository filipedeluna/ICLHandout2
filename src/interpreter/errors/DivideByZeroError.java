package interpreter.errors;

public class DivideByZeroError extends InterpreterError {
  public DivideByZeroError() {
    super("Cannot divide by zero.");
  }
}

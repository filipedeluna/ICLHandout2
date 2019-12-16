package interpreter.errors;

public abstract class InterpreterError extends Exception {
  public InterpreterError(String text) {
    super("Interpreter Error: " + text);
  }
}

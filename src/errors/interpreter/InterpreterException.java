package errors.interpreter;

public abstract class InterpreterException extends Exception {
  public InterpreterException(String text) {
    super("Interpreter Error: " + text);
  }
}

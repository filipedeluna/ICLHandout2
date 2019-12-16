package interpreter.errors;

public class UnexpectedTypeError extends InterpreterError {
  public UnexpectedTypeError(String type1, String type2) {
    super("Unexpected value of type " + type1 + " expected type " + type2 + ";");
  }
}

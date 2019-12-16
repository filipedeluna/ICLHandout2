package interpreter.errors;

public class InvalidArgumentsError extends InterpreterError {
  public InvalidArgumentsError(Enum operation) {
    super("Invalid arguments for operation " + operation.name());
  }
}

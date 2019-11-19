package errors.interpreter;

public class InvalidOperationArgumentsException extends InterpreterException {
  public InvalidOperationArgumentsException(Enum operation) {
    super("Invalid arguments for operation " + operation.name());
  }
}

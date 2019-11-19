package errors.interpreter;

public class CellNotFoundInMemoryException extends InterpreterException {
  public CellNotFoundInMemoryException() {
    super("Could not find cell in memory.");
  }
}

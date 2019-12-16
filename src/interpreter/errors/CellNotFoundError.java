package interpreter.errors;

public class CellNotFoundError extends InterpreterError {
  public CellNotFoundError() {
    super("Could not find cell in memory.");
  }
}

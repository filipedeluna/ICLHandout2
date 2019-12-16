package interpreter.errors;

public class MemoryCorruptionError extends InterpreterError {
  public MemoryCorruptionError() {
    super("Memory cell points to null value.");
  }
}

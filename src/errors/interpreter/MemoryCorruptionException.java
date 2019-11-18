package errors.interpreter;

public class MemoryCorruptionException extends InterpreterException {
  public MemoryCorruptionException() {
    super("Memory cell points to null value.");
  }
}

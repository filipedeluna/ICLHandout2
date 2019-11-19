package errors.compiler;

public class UndefinedOperationException extends CompilerException {
  public UndefinedOperationException(Enum operation) {
    super("Undefined operation " + operation.name());
  }
}

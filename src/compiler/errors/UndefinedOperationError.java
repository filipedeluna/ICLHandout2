package compiler.errors;

public class UndefinedOperationError extends CompilerError {
  public UndefinedOperationError(Enum operation) {
    super("Undefined operation " + operation.name());
  }
}

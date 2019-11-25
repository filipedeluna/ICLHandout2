package errors.compiler;

import value.IValue;

public class InvalidTypeException extends CompilerException {
  public InvalidTypeException(IValue type, String expected) {
    super("Invalid type to push " + type.typeToString() + " expected " + expected + ".");
  }
}
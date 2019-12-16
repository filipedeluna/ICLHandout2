package compiler.errors;

import values.IValue;

public class InvalidTypeError extends CompilerError {
  public InvalidTypeError(IValue type, String expected) {
    super("Invalid type to push " + type.type() + " expected " + expected + ".");
  }
}
package compiler.errors;

import types.IType;
import values.IValue;

public class CompileError extends Exception {
  public CompileError(String message, String operation, IType... types) {
    super("Compiler Error: " + build(message, operation, types, new IValue[0]));
  }

  public CompileError(String message, String operation, IValue... values) {
    super("Compiler Error: " + build(message, operation, new IType[0], values));
  }

  public CompileError(String message, String operation) {
    super("Compiler Error: " + build(message, operation, new IType[0], new IValue[0]));
  }

  private static String build(String message, String operation, IType[] types, IValue[] values) {
    StringBuilder sb = new StringBuilder().append(message).append(" on operation ").append(operation).append(" ");

    if (types.length > 0) {
      sb.append("with type");
      if (values.length > 1)
        sb.append("s");
      sb.append(": ");
      for (IType type : types)
        sb.append(type.name()).append(" ");
    }

    if (values.length > 0) {
      sb.append("with value");
      if (values.length > 1)
        sb.append("s");
      sb.append(": ");
      for (IValue value : values)
        sb.append(value.asString()).append(" ");
    }

    sb.append(".");

    return sb.toString();
  }
}

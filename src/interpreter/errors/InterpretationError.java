package interpreter.errors;

import types.IType;
import values.IValue;

public class InterpretationError extends Exception {
  public InterpretationError(String message, String operation, IType... types) {
    super("Interpretation Error: " + build(message, operation, types, new IValue[0]));
  }

  public InterpretationError(String message, String operation, IValue... values) {
    super("Interpretation Error: " + build(message, operation, new IType[0], values));
  }

  public InterpretationError(String message, String operation) {
    super("Interpretation Error: " + build(message, operation, new IType[0], new IValue[0]));
  }

  private static String build(String message, String operation, IType[] types, IValue[] values) {
    StringBuilder sb = new StringBuilder().append(message).append(" on operation ").append(operation).append(" ");

    if (types.length > 0) {
      sb.append("with type");
      if (types.length > 1)
        sb.append("s");
      sb.append(": ");
      for (IType type : types) {
        if (sb.length() > 0)
          sb.append(type.name());
        else
          sb.append(", ").append(type.name());
      }
    }

    if (values.length > 0) {
      sb.append("with value");
      if (values.length > 1)
        sb.append("s");
      sb.append(": ");
      for (IValue value : values) {
        if (sb.length() > 0)
          sb.append(value.asString());
        else
          sb.append(", ").append(value.asString());
      }
    }

    sb.append(".");

    return sb.toString();
  }
}
package typechecker.errors;

import types.IType;

public class TypeCheckError extends Exception {
  public TypeCheckError(String message, String operation, IType... types) {
    super("Type checker: " + build(message, operation, types));
  }

  public TypeCheckError(String message, String operation) {
    super("Type checker: " + build(message, operation));
  }

  private static String build(String message, String operation, IType... types) {
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

    sb.append(".");

    return sb.toString();
  }
}

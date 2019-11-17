package node.relational;

import env.Environment;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import errors.eval.IncompatibleTypesException;
import node.ASTNode;
import value.IValue;
import value.VBool;
import value.VInt;

public abstract class ASTRelational implements ASTNode {
  private ASTNode left;
  private ASTNode right;

  ASTRelational(ASTNode left, ASTNode right) {
    this.left = left;
    this.right = right;
  }

  protected IValue eval(RelationalOperation operation, Environment env) throws EvaluationException, EnvironmentException {
    IValue v1 = left.eval(env);
    IValue v2 = right.eval(env);

    if (v1 instanceof VInt && v2 instanceof VInt) {
      int i1 = ((VInt) v1).get();
      int i2 = ((VInt) v1).get();

      switch (operation) {
        case EQUALS:
          return new VBool(i1 == i2);
        case DIFFERS:
          return new VBool(i1 != i2);
        case GREATER_THAN:
          return new VBool(i1 > i2);
        case LOWER_THAN:
          return new VBool(i1 < i2);
        case GREATER_OR_EQUALS:
          return new VBool(i1 >= i2);
        case LOWER_OR_EQUALS:
          return new VBool(i1 <= i2);
      }
    }

    if (v1 instanceof VBool && v2 instanceof VBool) {
      boolean b1 = ((VBool) v1).get();
      boolean b2 = ((VBool) v1).get();

      switch (operation) {
        case EQUALS:
          return new VBool(b1 == b2);
        case DIFFERS:
          return new VBool(b1 != b2);
      }
    }

    throw new IncompatibleTypesException(operation.name(), v1, v2);
  }
}

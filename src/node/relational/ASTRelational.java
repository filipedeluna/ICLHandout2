package node.relational;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
import compiler.errors.UndefinedOperationError;
import interpreter.errors.InterpreterError;
import interpreter.errors.IncompatibleTypesError;
import node.ASTNode;
import values.IValue;
import values.VBool;
import values.VInt;

public class ASTRelational implements ASTNode {
  private RelationalOperation operation;
  private ASTNode left;
  private ASTNode right;

  public ASTRelational(RelationalOperation operation, ASTNode left, ASTNode right) {
    this.operation = operation;
    this.left = left;
    this.right = right;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    IValue v1 = left.eval(interpreter);
    IValue v2 = right.eval(interpreter);

    if (v1 instanceof VInt && v2 instanceof VInt) {
      int i1 = ((VInt) v1).get();
      int i2 = ((VInt) v2).get();

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

    throw new IncompatibleTypesError(operation.name(), v1, v2);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    left.compile(compiler);
    right.compile(compiler);

    switch (operation) {
      case EQUALS:
        compiler.compare(ByteCode.EQUALS);
        break;
      case DIFFERS:
        compiler.compare(ByteCode.NOT_EQUALS);
        break;
      case GREATER_THAN:
        compiler.compare(ByteCode.GREATER);
        break;
      case LOWER_THAN:
        compiler.compare(ByteCode.LESSER);
        break;
      case GREATER_OR_EQUALS:
        compiler.compare(ByteCode.GREATER_OR_EQ);
        break;
      case LOWER_OR_EQUALS:
        compiler.compare(ByteCode.LESSER_OR_EQ);
        break;
      default:
        throw new UndefinedOperationError(operation);
    }
  }
}

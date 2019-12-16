package node.relational;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TBool;
import types.TInt;
import types.TString;
import values.IValue;
import values.VBool;
import values.VInt;
import values.VString;

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
  public IValue eval(Interpreter interpreter) throws InterpretationError {
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
      boolean b2 = ((VBool) v2).get();

      switch (operation) {
        case EQUALS:
          return new VBool(b1 == b2);
        case DIFFERS:
          return new VBool(b1 != b2);
      }
    }

    if (v1 instanceof VString && v2 instanceof VString) {
      String s1 = ((VString) v1).get();
      String s2 = ((VString) v2).get();

      switch (operation) {
        case EQUALS:
          return new VBool(s1.compareTo(s2) == 0);
        case DIFFERS:
          return new VBool(s1.compareTo(s2) != 0);
      }
    }

    throw new InterpretationError("Invalid types for operation", operation.name(), v1.type(), v2.type());
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
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
        throw new CompileError("Undefined operation", operation.name());
    }
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type1 = left.typeCheck(typeChecker);
    IType type2 = right.typeCheck(typeChecker);

    if (!type1.equals(type2))
      throw new TypeCheckError("Incompatible types for operation", operation.name(), type1, type2);

    switch (operation) {
      case EQUALS:
      case DIFFERS:
        if (type1 instanceof TString || type1 instanceof TInt || type1 instanceof TBool)
          return TBool.SINGLETON;
        break;
      case GREATER_THAN:
      case LOWER_THAN:
      case GREATER_OR_EQUALS:
      case LOWER_OR_EQUALS:
        if (type1 instanceof TInt)
          return TBool.SINGLETON;
        break;
    }

    throw new TypeCheckError("Invalid type for operation", operation.name(), type1);
  }
}

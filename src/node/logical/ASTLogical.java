package node.logical;

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
import values.IValue;
import values.VBool;

public class ASTLogical implements ASTNode {
  private LogicalOperation operation;
  private ASTNode left;
  private ASTNode right;

  public ASTLogical(LogicalOperation operation, ASTNode left, ASTNode right) {
    this.operation = operation;
    this.left = left;
    this.right = right;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue v1 = left.eval(interpreter);
    IValue v2 = right.eval(interpreter);

    if (v1 instanceof VBool && v2 instanceof VBool) {
      boolean b1 = ((VBool) v1).get();
      boolean b2 = ((VBool) v2).get();

      switch (operation) {
        case AND:
          return new VBool(b1 && b2);
        case OR:
          return new VBool(b1 || b2);
      }
    }

    throw new InterpretationError("Invalid types for operation", operation.name(), v1.type(), v2.type());
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    left.compile(compiler);
    right.compile(compiler);

    switch (operation) {
      case AND:
        compiler.emit(ByteCode.AND);
        break;
      case OR:
        compiler.emit(ByteCode.OR);
        break;
      default:
        throw new CompileError("Undefined operation", operation.name());
    }
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type1 = left.typeCheck(typeChecker);
    IType type2 = right.typeCheck(typeChecker);

    if (!(type1 instanceof TBool) && !(type2 instanceof TBool))
      throw new TypeCheckError("Invalid types for operation", operation.name(), type1, type2);

    return TBool.SINGLETON;
  }
}

package node.logical;

import compiler.ByteCode;
import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.compiler.UndefinedOperationException;
import errors.interpreter.InterpreterException;
import errors.interpreter.IncompatibleTypesException;
import errors.interpreter.InvalidOperationArgumentsException;
import node.ASTNode;
import value.IValue;
import value.VBool;

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
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue v1 = left.eval(interpreter);
    IValue v2 = right.eval(interpreter);

    if (v1 instanceof VBool && v2 instanceof VBool) {
      boolean b1 = ((VBool) v1).get();
      boolean b2 = ((VBool) v1).get();

      switch (operation) {
        case AND:
          return new VBool(b1 && b2);
        case OR:
          return new VBool(b1 || b2);
      }
    }

    throw new IncompatibleTypesException(operation.name(), v1, v2);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
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
        throw new UndefinedOperationException(operation);
    }
  }
}

package node.logical;

import compiler.ByteCode;
import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import errors.interpreter.IncompatibleTypesException;
import node.ASTNode;
import value.IValue;
import value.VBool;

abstract class ASTLogical implements ASTNode {
  private ASTNode left;
  private ASTNode right;

  ASTLogical(ASTNode left, ASTNode right) {
    this.left = left;
    this.right = right;
  }

  ASTLogical(ASTNode node) {
    this.left = node;
    this.right = node;
  }

  protected IValue eval(LogicalOperation operation, Interpreter interpreter) throws InterpreterException {
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
        case COMP:
          return new VBool(!b1);
      }
    }

    throw new IncompatibleTypesException(operation.name(), v1, v2);
  }

  protected void compile(LogicalOperation operation, Compiler compiler) throws CompilerException {
    left.compile(compiler);
    right.compile(compiler);

    switch (operation) {
      case AND:
        compiler.emit(ByteCode.AND);
        break;
      case OR:
        compiler.emit(ByteCode.OR);
        break;
      case COMP:
        compiler.emit(ByteCode.PUSH, "-1");
        compiler.emit(ByteCode.XOR);
        break;
    }
  }
}

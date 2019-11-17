package node.logical;

import compiler.ByteCode;
import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import errors.eval.IncompatibleTypesException;
import node.ASTNode;
import value.IValue;
import value.VBool;

public abstract class ASTLogical implements ASTNode {
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

  protected IValue eval(LogicalOperation operation, Environment env) throws EvaluationException, EnvironmentException {
    IValue v1 = left.eval(env);
    IValue v2 = right.eval(env);

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

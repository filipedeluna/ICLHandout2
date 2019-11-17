package node.arithmetic;

import compiler.ByteCode;
import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import errors.eval.IncompatibleTypesException;
import node.ASTNode;
import value.IValue;
import value.VInt;

public abstract class ASTArithmetic implements ASTNode {
  private ASTNode left;
  private ASTNode right;

  ASTArithmetic(ASTNode left, ASTNode right) {
    this.left = left;
    this.right = right;
  }

  ASTArithmetic(ASTNode node) {
    this.left = node;
    this.right = node;
  }

  protected IValue eval(ArithmeticOperation operation, Environment env) throws EvaluationException, EnvironmentException {
    IValue v1 = left.eval(env);
    IValue v2 = right.eval(env);

    if (v1 instanceof VInt && v2 instanceof VInt) {
      int i1 = ((VInt) v1).get();
      int i2 = ((VInt) v1).get();

      switch (operation) {
        case ADD:
          return new VInt(i1 + i2);
        case SUB:
          return new VInt(i1 - i2);
        case MUL:
          return new VInt(i1 * i2);
        case DIV:
          return new VInt(i1 / i2);
        case NEG:
          return new VInt(-i1);
      }
    }

    throw new IncompatibleTypesException(operation.name(), v1, v2);
  }

  protected void compile(ArithmeticOperation operation, Compiler compiler) throws CompilerException {
    left.compile(compiler);
    right.compile(compiler);

    switch (operation) {
      case ADD:
        compiler.emit(ByteCode.ADD);
        break;
      case SUB:
        compiler.emit(ByteCode.SUB);
        break;
      case MUL:
        compiler.emit(ByteCode.MUL);
        break;
      case DIV:
        compiler.emit(ByteCode.DIV);
        break;
      case NEG:
        compiler.emit(ByteCode.NEG);
        break;
    }
  }
}

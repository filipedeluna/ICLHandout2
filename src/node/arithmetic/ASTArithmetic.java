package node.arithmetic;

import compiler.ByteCode;
import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.compiler.UndefinedOperationException;
import errors.interpreter.DivideByZeroException;
import errors.interpreter.IncompatibleTypesException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;
import value.VInt;

public class ASTArithmetic implements ASTNode {
  private ArithmeticOperation operation;
  private ASTNode left;
  private ASTNode right;

  public ASTArithmetic(ArithmeticOperation operation, ASTNode left, ASTNode right) {
    this.operation = operation;
    this.left = left;
    this.right = right;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue v1 = left.eval(interpreter);
    IValue v2 = right.eval(interpreter);

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
          if (i2 == 0)
            throw new DivideByZeroException();
          return new VInt(i1 / i2);
      }
    }

    throw new IncompatibleTypesException(operation.name(), v1, v2);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
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
      default:
        throw new UndefinedOperationException(operation);
    }
  }
}

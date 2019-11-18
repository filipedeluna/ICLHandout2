package node.arithmetic;

import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public final class ASTSub extends ASTArithmetic {
  public ASTSub(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return eval(ArithmeticOperation.SUB, interpreter);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compile(ArithmeticOperation.SUB, compiler);
  }
}

package node.arithmetic;

import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public final class ASTNeg extends ASTArithmetic {
  public ASTNeg(ASTNode node) {
    super(node);
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return eval(ArithmeticOperation.MUL, interpreter);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compile(ArithmeticOperation.MUL, compiler);
  }
}

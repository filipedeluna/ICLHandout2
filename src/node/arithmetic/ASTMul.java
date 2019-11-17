package node.arithmetic;

import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.eval.EvaluationException;
import errors.env.EnvironmentException;
import node.ASTNode;
import value.IValue;

public class ASTMul extends ASTArithmetic {
  public ASTMul(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Environment env) throws EvaluationException, EnvironmentException {
    return eval(ArithmeticOperation.MUL, env);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compile(ArithmeticOperation.MUL, compiler);
  }
}

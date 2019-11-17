package node.arithmetic;

import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;
import value.IValue;

public class ASTDiv extends ASTArithmetic {
  public ASTDiv(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Environment env) throws EvaluationException, EnvironmentException {
    return eval(ArithmeticOperation.DIV, env);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compile(ArithmeticOperation.DIV, compiler);
  }
}

package node.arithmetic;

import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;
import value.IValue;

public class ASTNeg extends ASTArithmetic {
  public ASTNeg(ASTNode node) {
    super(node);
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

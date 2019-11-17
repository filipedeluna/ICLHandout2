package node.relational;

import compiler.Compiler;
import env.Environment;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;
import value.IValue;

public class ASTEq extends ASTRelational {
  public ASTEq(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Environment env) throws EvaluationException, EnvironmentException {
    return eval(RelationalOperation.EQUALS, env);
  }

  @Override
  public void compile(Compiler compiler) {

  }
}

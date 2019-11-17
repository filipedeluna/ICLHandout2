package node.relational;

import compiler.Compiler;
import env.Environment;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;
import value.IValue;

public class ASTGreaterOrEq extends ASTRelational {
  public ASTGreaterOrEq(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Environment env) throws EvaluationException, EnvironmentException {
    return eval(RelationalOperation.GREATER_OR_EQUALS, env);
  }

  @Override
  public void compile(Compiler compiler) {

  }
}

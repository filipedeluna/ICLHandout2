package node.logic;

import compiler.Compiler;
import env.Environment;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;

public class ASTEq implements ASTNode {
  private ASTNode left;
  private ASTNode right;

  public ASTEq(ASTNode left, ASTNode right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public int eval(Environment env) throws EvaluationException, EnvironmentException {
    int v1 = left.eval(env);
    int v2 = right.eval(env);

    return v1 == v2 ? 1 : 0;
  }

  @Override
  public void compile(Compiler compiler) {

  }
}

package node.block;

import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;

public class ASTAssign implements ASTNode {
  private String id;
  private ASTNode node;

  public ASTAssign(String id, ASTNode node) {
    this.id = id;
    this.node = node;
  }

  @Override
  public int eval(Environment env) throws EvaluationException, EnvironmentException {
    int val = node.eval(env);

    env.associate(id, val);

    return 1;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.loadStaticLink();

    node.compile(compiler);

    compiler.addFieldToFrame(id);
  }
}

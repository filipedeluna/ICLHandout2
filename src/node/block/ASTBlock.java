package node.block;

import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;

import java.util.HashSet;

public class ASTBlock implements ASTNode {
  private HashSet<ASTAssign> assignments;
  private ASTNode block;

  public ASTBlock(HashSet<ASTAssign> assignments, ASTNode block) {
    this.assignments = assignments;
    this.block = block;
  }

  public ASTBlock(ASTNode block) {
    this.block = block;
  }

  public int eval(Environment env) throws EvaluationException, EnvironmentException {
    env.beginScope();

    if (assignments != null)
      for (ASTAssign assignment : assignments)
        assignment.eval(env);

    int val = block.eval(env);

    env.endScope();

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.beginFrame();

    if (assignments != null)
      for (ASTAssign assignment : assignments)
        assignment.compile(compiler);

    block.compile(compiler);

    compiler.endFrame();
  }
}

package node.logic;

import compiler.ByteCode;
import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;

public class ASTNeg implements ASTNode {
  private ASTNode node;

  public ASTNeg(ASTNode node) {
    this.node = node;
  }

  @Override
  public int eval(Environment env) throws EvaluationException, EnvironmentException {
    return -node.eval(env);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    node.compile(compiler);

    compiler.emit(ByteCode.PUSH, "-1");
    compiler.emit(ByteCode.MUL);
  }
}

package node.delimiter;

import compiler.Compiler;
import env.Environment;
import node.ASTNode;
import node.op.ASTOp;

public class ASTSeq extends ASTOp {
  public ASTSeq(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public int eval(Environment env) {
    return 0;
  }

  @Override
  public void compile(Compiler compiler) {

  }
}

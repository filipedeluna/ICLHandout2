package node.op;

import node.ASTNode;

public abstract class ASTOp implements ASTNode {
  private ASTNode left;
  private ASTNode right;

  public ASTOp(ASTNode left, ASTNode right) {
    this.left = left;
    this.right = right;
  }

  protected ASTNode getLeft() {
    return left;
  }

  protected ASTNode getRight() {
    return right;
  }
}

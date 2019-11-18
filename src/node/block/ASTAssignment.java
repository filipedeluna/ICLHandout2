package node.block;

import compiler.Compiler;
import errors.compiler.CompilerException;
import node.ASTNode;

abstract class ASTAssignment implements ASTNode {
  private String id;
  private ASTNode node;

  public ASTAssignment(String id, ASTNode node) {
    this.id = id;
    this.node = node;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.loadStaticLink();

    node.compile(compiler);

    compiler.addFieldToFrame(id);
  }
}

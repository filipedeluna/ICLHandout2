package compiler.frame;

import compiler.CompilerType;
import node.ASTNode;

import java.util.ArrayList;

public final class FrameFunctionField extends FrameField {
  private ArrayList<CompilerType> params;
  private CompilerType returnType;
  private ASTNode node;

  public FrameFunctionField(String fieldId, ASTNode node, ArrayList<CompilerType> params, CompilerType returnType) {
    super(fieldId, CompilerType.FUN);
    this.params = params;
    this.returnType = returnType;
    this.node = node;
  }

  public ArrayList<CompilerType> getParams() {
    return params;
  }

  public CompilerType getReturnType() {
    return returnType;
  }

  public ASTNode getNode() {
    return node;
  }
}

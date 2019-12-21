package compiler.frame;

import compiler.CompilerType;
import node.ASTNode;

import java.util.LinkedHashMap;

public final class FrameFunctionField extends FrameField {
  private LinkedHashMap<String, CompilerType> params;
  private CompilerType returnType;
  private ASTNode node;

  public FrameFunctionField(String fieldId, ASTNode node, LinkedHashMap<String, CompilerType> params, CompilerType returnType) {
    super(fieldId, CompilerType.FUN);
    this.params = params;
    this.returnType = returnType;
    this.node = node;
  }

  public LinkedHashMap<String, CompilerType> getParams() {
    return params;
  }

  public CompilerType getReturnType() {
    return returnType;
  }

  public ASTNode getNode() {
    return node;
  }
}

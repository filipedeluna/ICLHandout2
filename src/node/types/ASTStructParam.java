package node.types;

import node.ASTNode;
import types.IType;
import types.TBool;
import types.TFun;
import types.TInt;

public class ASTStructParam {
  private String id;
  private IType type;

  public ASTStructParam(String id, ASTNode node) {
    this.id = id;


  }

  public String getId() {
    return id;
  }

  public IType getType() {
    return type;
  }
}

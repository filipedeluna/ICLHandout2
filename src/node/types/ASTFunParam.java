package node.types;

import types.TBool;
import types.TFun;
import types.IType;
import types.TInt;

public class ASTFunParam {
  private String id;
  private IType type;

  public ASTFunParam(String id, IType type) {
    this.id = id;


  }

  public String getId() {
    return id;
  }

  public IType getType() {
    return type;
  }
}

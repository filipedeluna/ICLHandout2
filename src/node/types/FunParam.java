package node.types;

import types.*;

public class FunParam {
  private String id;
  private IType type;

  public FunParam(String id, IType type) {
    this.id = id;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public IType getType() {
    return type;
  }
}

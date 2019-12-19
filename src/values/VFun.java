package values;

import node.ASTNode;
import types.IType;
import types.TFun;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public final class VFun implements IValue {
  private ASTNode block;
  private LinkedHashMap<String, IType> params;

  public VFun(LinkedHashMap<String, IType> params, ASTNode block) {
    this.block = block;
    this.params = params;
  }

  public ASTNode getBlock() {
    return block;
  }

  public LinkedHashMap<String, IType> getParams() {
    return params;
  }

  @Override
  public boolean equals(IValue value) {
    return false;
  }

  public IType type() {
    return TFun.SINGLETON;
  }

  @Override
  public String asString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("fun (");

    for (Entry<String, IType> param : params.entrySet()) {
      stringBuilder
          .append(param.getKey())
          .append(":")
          .append(param.getValue().name())
          .append(" ");
    }

    stringBuilder.append(")");

    return stringBuilder.toString();
  }
}

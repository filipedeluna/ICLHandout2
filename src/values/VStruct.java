package values;

import node.ASTNode;
import node.types.ASTStructParam;
import types.TFun;
import types.IType;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public final class VStruct implements IValue {
  private TFun type;
  private LinkedHashMap<String, IValue> parameters;
  private ASTNode block;

  public VStruct(ArrayList<ASTStructParam> parameters) {
    this.block = block;

    // Build types for type variable
    ArrayList<IType> functionParamTypes = new ArrayList<>();
    for (IValue value : parameters.values())
      functionParamTypes.add(value.type());

    type = new TFun(functionParamTypes);
  }

  @Override
  public boolean equals(IValue value) {
    return false;
  }

  public IType type() {
    return type;
  }

  @Override
  public String asString() {
    return type.name();
  }

  public boolean equals(TFun function) {
    return function.equals(function);
  }
}

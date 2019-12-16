package values;

import node.ASTNode;
import node.types.ASTFunParam;
import types.TFun;
import types.IType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public final class VFun implements IValue {
  private TFun type;
  private LinkedHashMap<String, IValue> parameters;
  private ASTNode block;

  public VFun(ArrayList<ASTFunParam> funParams, ASTNode block) {
    this.block = block;

    // Build types for type variable
    ArrayList<IType> functionParamTypes = new ArrayList<>();
    for (IValue value : parameters.values())
      functionParamTypes.add(value.type());

    // TODO GET RETURN TYPE
    type = new TFun(functionParamTypes);
  }

  public IValue getParameter(String id) {
    return parameters.get(id);
  }

  public VFun(LinkedHashMap<String, IValue> parameters) {
    this.parameters = parameters;
  }

  public ASTNode getBlock() {
    return block;
  }

  @Override
  public boolean equals(IValue value) {
    if (!(value instanceof VFun))
      return false;

    if (!(value.type().equals(type)))
      return false;

    for (Entry<String, IValue> param : parameters.entrySet()) {
      if (!param.getValue().equals(((VFun) value).getParameter(param.getKey())))
        return false;
    }

    return true;
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

package types;

import java.util.ArrayList;

public class TFun implements IType {
  public final static TFun SINGLETON = new TFun(new ArrayList<>());

  private ArrayList<IType> paramTypes;
  private IType returnType;

  public TFun(ArrayList<IType> paramTypes) {
    this.paramTypes = paramTypes;
    this.returnType = TVoid.SINGLETON;
  }

  public TFun(ArrayList<IType> paramTypes, IType returnType) {
    this.paramTypes = paramTypes;
    this.returnType = returnType;
  }

  public ArrayList<IType> getParameterTypes() {
    return paramTypes;
  }

  public IType getReturnType() {
    return returnType;
  }

  @Override
  public boolean equals(IType type) {
    if (!(type instanceof TFun))
      return false;

    ArrayList<IType> secTypes = ((TFun) type).getParameterTypes();

    if (secTypes.size() != paramTypes.size())
      return false;

    for (int i = 0; i < secTypes.size(); i++) {
      if (!paramTypes.get(i).equals(paramTypes.get(i)))
        return false;
    }

    return true;
  }

  @Override
  public String name() {
    return "fun";
  }
}

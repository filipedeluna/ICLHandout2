package types;

import java.util.ArrayList;

public class TFun implements IType {
  private ArrayList<IType> paramTypes;
  private IType returnType;

  public TFun(ArrayList<IType> paramTypes, IType returnType) {
    this.paramTypes = paramTypes;
    this.returnType = returnType;
  }

  public TFun(ArrayList<IType> paramTypes) {
    this.paramTypes = paramTypes;
    this.returnType = TVoid.SINGLETON;
  }


  private ArrayList<IType> getParameterTypes() {
    return paramTypes;
  }

  public IType getReturnType() {
    return returnType;
  }

  @Override
  public boolean equals(IType type) {
    if (!(type instanceof TFun))
      return false;

    if (!returnType.equals(((TFun) type).getReturnType()))
      return false;

    ArrayList<IType> paramTypes2 = ((TFun) type).getParameterTypes();

    if (paramTypes.size() != paramTypes2.size())
      return false;

    for (int i = 0; i < paramTypes.size(); i++) {
      if (!paramTypes.get(i).equals(paramTypes2.get(i)))
        return false;
    }

    return true;
  }

  @Override
  public String name() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("fun (");

    for (int i = 0; i < paramTypes.size(); i++) {
      stringBuilder.append(paramTypes.get(i).name());

      if (i < paramTypes.size() - 1)
        stringBuilder.append(',');
    }

    stringBuilder.append(")");

    if (!(returnType instanceof TVoid))
      stringBuilder.append(" " + returnType.name());

    return stringBuilder.toString();
  }
}

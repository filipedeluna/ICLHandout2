package types;

public class TStruct implements IType {
  public final static TStruct SINGLETON = new TStruct();

  public TStruct() {
  }

  @Override
  public boolean equals(IType type) {
    return type instanceof TStruct;
  }

  @Override
  public String name() {
    return "struct";
  }
}

package types;

public final class TInt implements IType {
  public final static TInt SINGLETON = new TInt();

  public TInt() {
  }

  @Override
  public boolean equals(IType type) {
    return type instanceof TInt;
  }

  @Override
  public String name() {
    return "int";
  }
}

package types;

public final class TBool implements IType {
  public final static TBool SINGLETON = new TBool();

  public TBool() {
  }

  @Override
  public boolean equals(IType type) {
    return type instanceof TBool;
  }

  @Override
  public String name() {
    return "boolean";
  }
}

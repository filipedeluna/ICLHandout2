package types;

public final class TString implements IType {
  public final static TString SINGLETON = new TString();

  public TString() {
  }

  @Override
  public boolean equals(IType type) {
    return type instanceof TString;
  }

  @Override
  public String name() {
    return "void";
  }
}

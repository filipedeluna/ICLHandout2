package types;

public final class TVoid implements IType {
  public final static TVoid SINGLETON = new TVoid();

  public TVoid() {
  }

  @Override
  public boolean equals(IType type) {
    return type instanceof TVoid;
  }

  @Override
  public String name() {
    return "void";
  }
}

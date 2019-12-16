package types;

public final class TCell implements IType {
  public final static TCell SINGLETON = new TCell();

  public TCell() {
  }

  @Override
  public boolean equals(IType type) {
    return type instanceof TCell;
  }

  @Override
  public String name() {
    return "cell";
  }
}

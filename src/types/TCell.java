package types;

public class TCell implements IType {
  public final static TCell SINGLETON = new TCell();

  private String id;

  private TCell() {
  }

  public TCell(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
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

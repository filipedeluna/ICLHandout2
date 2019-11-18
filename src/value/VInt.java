package value;

public final class VInt implements IValue {
  private int value;

  public VInt(int value) {
    this.value = value;
  }

  public int get() {
    return value;
  }

  public String typeToString() {
    return "int";
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @Override
  public void show() {

  }
}

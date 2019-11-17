package value;

public class VInt implements IValue {
  private int value;

  public VInt(int value) {
    this.value = value;
  }

  public int get() {
    return value;
  }

  public String type() {
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

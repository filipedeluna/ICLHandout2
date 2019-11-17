package value;

public class VBool implements IValue {
  private boolean value;

  public VBool(boolean value) {
    this.value = value;
  }

  public boolean get() {
    return value;
  }

  public String type() {
    return "bool";
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @Override
  public void show() {

  }
}

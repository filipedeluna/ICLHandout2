package value;

public final class VCell implements IValue {
  private int address;

  public VCell(int address) {
    this.address = address;
  }

  public int getAddress() {
    return address;
  }

  public String typeToString() {
    return "cell";
  }

  @Override
  public void show() {

  }
}

package value;

public class VClosure implements IValue {
  private int v;

  public VClosure(int v) {
    this.v = v;
  }

  public int getVal() {
    return v;
  }

  @Override
  public void show() {

  }
}

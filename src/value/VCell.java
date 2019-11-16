package value;

public class VCell implements IValue {
  private IValue iv;

  VCell(IValue iv) {
    this.iv = iv;
  }

  public IValue getVal() {
    return iv;
  }

  public void setVal(IValue iv) {
    this.iv = iv;
  }

  @Override
  public void show() {

  }
}

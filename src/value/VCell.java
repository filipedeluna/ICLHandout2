package value;

public class VCell implements IValue {
  private IValue value;

  public VCell(IValue value) {
    this.value = value;
  }

  public IValue get() {
    return value;
  }

  public String type() {
    return "cell";
  }

  @Override
  public void show() {

  }
}

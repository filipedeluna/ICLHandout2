package values;

import types.TCell;
import types.IType;

public final class VCell implements IValue {
  private int address;

  public VCell(int address) {
    this.address = address;
  }

  public int get() {
    return address;
  }

  @Override
  public boolean equals(IValue value) {
    return value instanceof VCell && ((VCell) value).get() == address;
  }

  public IType type() {
    return TCell.SINGLETON;
  }

  @Override
  public String asString() {
    return null;
  }
}

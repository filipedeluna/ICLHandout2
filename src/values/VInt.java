package values;

import types.IType;
import types.TInt;

public final class VInt implements IValue {
  private int val;

  public VInt(int val) {
    this.val = val;
  }

  public int get() {
    return val;
  }

  @Override
  public boolean equals(IValue value) {
    return value instanceof VInt && ((VInt) value).get() == val;
  }

  public IType type() {
    return TInt.SINGLETON;
  }

  @Override
  public String asString() {
    return String.valueOf(val);
  }
}

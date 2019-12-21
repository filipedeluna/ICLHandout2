package values;

import types.TBool;
import types.IType;

public final class VBool implements IValue {
  private boolean val;

  public VBool(boolean val) {
    this.val = val;
  }

  public boolean get() {
    return val;
  }

  @Override
  public boolean equals(IValue value) {
    return value instanceof VBool && ((VBool) value).get() == val;
  }

  public IType type() {
    return TBool.SINGLETON;
  }

  @Override
  public String asString() {
    return val ? "true" : "false";
  }
}

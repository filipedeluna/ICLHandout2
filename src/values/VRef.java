package values;

import types.IType;
import types.TString;

public final class VRef implements IValue {
  private String val;

  public VRef(String val) {
    this.val = val;
  }

  public String get() {
    return val;
  }

  @Override
  public boolean equals(IValue value) {
    return value instanceof VRef && ((VRef) value).get().equals(val);
  }

  public IType type() {
    return TString.SINGLETON;
  }

  @Override
  public String asString() {
    return val;
  }
}

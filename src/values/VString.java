package values;

import types.IType;
import types.TString;

public final class VString implements IValue {
  private String val;

  public VString(String val) {
    this.val = val;
  }

  public String get() {
    return val;
  }

  @Override
  public boolean equals(IValue value) {
    return value instanceof VString && ((VString) value).get().equals(val);
  }

  public IType type() {
    return TString.SINGLETON;
  }

  @Override
  public String asString() {
    return val;
  }
}

package values;

import types.IType;
import types.TString;

public final class VString implements IValue {
  private String val;


  public VString(String val, boolean trim) {
    if (trim)
      this.val = val.substring(1, val.length() - 1);
    else
      this.val = val;
  }

  @Override
  public boolean equals(IValue value) {
    return value instanceof VString && value.asString().equals(val);
  }

  public IType type() {
    return TString.SINGLETON;
  }

  @Override
  public String asString() {
    return val;
  }
}

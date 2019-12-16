package values;

import types.IType;

public interface IValue {
  boolean equals(IValue value);

  IType type();

  String asString();
}

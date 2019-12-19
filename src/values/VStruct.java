package values;

import types.IType;
import types.TStruct;

import java.util.HashMap;
import java.util.Map.Entry;

public final class VStruct implements IValue {
  HashMap<String, IValue> values;

  public VStruct() {
    values = new HashMap<>();
  }

  public void put(String id, IValue value) {
    values.put(id, value);
  }

  public IValue get(String id) {
    return values.get(id);
  }

  public boolean contains(String id) {
    return values.containsKey(id);
  }

  public boolean merge(VStruct struct) {
    HashMap<String, IValue> secondaryValues = struct.getFields();

    for (Entry<String, IValue> entry : secondaryValues.entrySet()) {
      if (values.containsKey(entry.getKey()))
        return false;

      values.put(entry.getKey(), entry.getValue());
    }

    return true;
  }

  public HashMap<String, IValue> getFields() {
    return values;
  }

  @Override
  public boolean equals(IValue value) {
    return value instanceof VStruct;
  }

  public IType type() {
    return TStruct.SINGLETON;
  }

  @Override
  public String asString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("{ ");

    for (Entry<String, IValue> parameter : values.entrySet()) {
      stringBuilder
          .append(parameter.getKey())
          .append("=")
          .append(parameter.getValue().asString())
          .append(" ");
    }

    stringBuilder.append("}");

    return stringBuilder.toString();
  }
}

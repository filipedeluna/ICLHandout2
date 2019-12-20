package types;

import values.IValue;

import java.util.HashMap;
import java.util.Map.Entry;

public class TStruct implements IType {
  public final static TStruct SINGLETON = new TStruct();

  private HashMap<String, IType> fields;

  public TStruct() {
    this.fields = new HashMap<>();
  }

  public void put(String id, IType type) {
    fields.put(id, type);
  }

  public boolean contains(String id) {
    return fields.containsKey(id);
  }

  public IType get(String id) {
    return fields.get(id);
  }

  public boolean merge(TStruct struct) {
    HashMap<String, IType> secondaryValues = struct.getFields();

    for (Entry<String, IType> entry : secondaryValues.entrySet()) {
      if (fields.containsKey(entry.getKey()))
        return false;

      fields.put(entry.getKey(), entry.getValue());
    }

    return true;
  }

  public HashMap<String, IType> getFields() {
    return fields;
  }

  @Override
  public boolean equals(IType type) {
    return type instanceof TStruct;
  }

  @Override
  public String name() {
    return "struct";
  }
}

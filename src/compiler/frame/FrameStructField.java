package compiler.frame;

import compiler.CompilerType;

import java.util.LinkedHashMap;

public final class FrameStructField extends FrameField {
  private LinkedHashMap<String, CompilerType> entries;

  public FrameStructField(String fieldId) {
    super(fieldId, CompilerType.STRUCT);
    entries = new LinkedHashMap<>();
  }

  public void addStructField(String fieldId, CompilerType type) {
    entries.put(fieldId, type);
  }

  public LinkedHashMap<String, CompilerType> getEntries() {
    return entries;
  }

  public CompilerType getStructFieldType(String fieldId) {
    return entries.get(fieldId);
  }

  public boolean hasField(String fieldId) {
    return entries.containsKey(fieldId);
  }
}

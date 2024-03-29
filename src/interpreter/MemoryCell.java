package interpreter;

import values.IValue;

final class MemoryCell {
  private int references;
  private IValue value;

  MemoryCell(IValue value) {
    this.value = value;
    references = 1;
  }

  MemoryCell(MemoryCell cell) {
    value = cell.getValue();
    references = cell.references();
  }

  void incReferences() {
    references++;
  }

  void decReferences() {
    references--;
  }

  int references() {
    return references;
  }

  public IValue getValue() {
    return value;
  }
}

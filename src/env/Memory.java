package env;

import errors.interpreter.MemoryCorruptionException;
import errors.interpreter.ValueNotFoundInMemoryException;
import value.IValue;
import value.VCell;

import java.util.HashMap;

class Memory {
  private HashMap<Integer, MemoryCell> memory;
  private int addressCounter;

  Memory() {
    memory = new HashMap<>();
    addressCounter = 0;
  }

  int addCell(IValue value) {
    if (value instanceof VCell)
      memory.get(((VCell) value).getAddress()).incReferences();

    memory.put(addressCounter, new MemoryCell(value));

    return addressCounter++;
  }

  void removeCellReference(int address) {
    MemoryCell cell = memory.get(address);

    cell.decReferences();

    if (cell.references() == 0)
      memory.remove(address);
  }

  // Recursively obtain memory value because cells can point to other cells
  IValue getCellValue(int address) throws ValueNotFoundInMemoryException, MemoryCorruptionException {
    MemoryCell cell = memory.get(address);

    if (cell == null)
      throw new ValueNotFoundInMemoryException();

    IValue value = cell.getValue();

    if (value == null) {
      throw new MemoryCorruptionException();
    }

    if (value instanceof VCell)
      value = getCellValue(((VCell) value).getAddress());

    return value;
  }
}

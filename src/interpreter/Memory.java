package interpreter;

import interpreter.errors.InterpretationError;
import values.IValue;
import values.VCell;

import java.util.HashMap;

class Memory {
  private HashMap<Integer, MemoryCell> memory;
  private int addressCounter;

  Memory() {
    memory = new HashMap<>();
    addressCounter = 0;
  }

  int addCell(IValue value) throws InterpretationError {
    if (value instanceof VCell) {
      int cellRefAddress = ((VCell) value).get();
      addCellReference(cellRefAddress);
    }

    memory.put(addressCounter, new MemoryCell(value));

    return addressCounter++;
  }

  // Recursively obtain memory value because cells can point to other cells
  IValue getCellValue(int address) throws InterpretationError {
    MemoryCell cell = getCell(address);

    IValue value = cell.getValue();

    if (value == null)
      throw new InterpretationError("Memory reference is corrupted", "get value from memory");

    if (value instanceof VCell)
      value = getCellValue(((VCell) value).get());

    return value;
  }

  void changeCellValue(int address, IValue value) throws InterpretationError {
    MemoryCell oldCell = getCell(address);

    memory.remove(address);

    if (oldCell.getValue() instanceof VCell) {
      int oldCellValueAddress = ((VCell) oldCell.getValue()).get();
      removeCellReference(oldCellValueAddress);
    }

    memory.put(address, new MemoryCell(value));

    if (value instanceof VCell)
      addCellReference(((VCell) value).get());
  }

  void addCellReference(int address) throws InterpretationError {
    MemoryCell oldCell = getCell(address);

    oldCell.incReferences();

    memory.put(address, new MemoryCell(oldCell));
  }

  void removeCellReference(int address) throws InterpretationError {
    MemoryCell oldCell = getCell(address);

    oldCell.decReferences();

    if (oldCell.references() == 0)
      memory.remove(address);
    else
      memory.put(address, new MemoryCell(oldCell));
  }

  /*
    UTILS
  */
  private MemoryCell getCell(int address) throws InterpretationError {
    MemoryCell cell = memory.get(address);

    if (cell == null)
      throw new InterpretationError("Memory reference not found", "get reference from memory");

    return cell;
  }
}

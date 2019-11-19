package env;

import errors.interpreter.MemoryCorruptionException;
import errors.interpreter.CellNotFoundInMemoryException;
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

  int addCell(IValue value) throws CellNotFoundInMemoryException {
    if (value instanceof VCell) {
      int cellRefAddress = ((VCell) value).getAddress();

      addCellReference(cellRefAddress);
    }

    memory.put(addressCounter, new MemoryCell(value));

    return addressCounter++;
  }

  void addCellReference(int address) throws CellNotFoundInMemoryException {
    MemoryCell oldCell = getCell(address);

    oldCell.incReferences();

    memory.put(address, new MemoryCell(oldCell));
  }

  void removeCellReference(int address) throws CellNotFoundInMemoryException {
    MemoryCell oldCell = getCell(address);

    oldCell.decReferences();

    if (oldCell.references() == 0)
      memory.remove(address);
    else
      memory.put(address, new MemoryCell(oldCell));
  }

  // Recursively obtain memory value because cells can point to other cells
  IValue getCellValue(int address) throws CellNotFoundInMemoryException, MemoryCorruptionException {
    MemoryCell cell = getCell(address);

    IValue value = cell.getValue();

    if (value == null)
      throw new MemoryCorruptionException();

    if (value instanceof VCell)
      value = getCellValue(((VCell) value).getAddress());

    return value;
  }

  void changeCellValue(int address, IValue value) throws CellNotFoundInMemoryException, MemoryCorruptionException {
    MemoryCell oldCell = getCell(address);

    memory.remove(address);

    if (oldCell.getValue() instanceof VCell) {
      int oldCellValueAddress = ((VCell) oldCell.getValue()).getAddress();

      removeCellReference(oldCellValueAddress);
    }

    memory.put(address, new MemoryCell(value));

    if (value instanceof VCell)
      addCellReference(((VCell) value).getAddress());
  }

  /*
    UTILS
  */
  private MemoryCell getCell(int address) throws CellNotFoundInMemoryException {
    MemoryCell cell = memory.get(address);

    if (cell == null)
      throw new CellNotFoundInMemoryException();

    return cell;
  }
}

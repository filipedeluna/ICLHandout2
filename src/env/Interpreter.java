package env;

import errors.interpreter.*;
import value.IValue;
import value.VBool;
import value.VCell;
import value.VInt;

import java.util.HashSet;

public class Interpreter {
  private Environment environment;
  private Memory memory;

  public Interpreter() {
    environment = new Environment();
    memory = new Memory();

    // Begin main program scope
    environment.beginScope();
  }

  public void beginEnvScope() {
    environment.beginScope();
  }

  public void endEnvScope() throws CellNotFoundInMemoryException {
    HashSet<VCell> cells = environment.currentScopeCells();

    for (VCell cell : cells) {
      memory.removeCellReference(cell.getAddress());
    }

    environment.endScope();
  }

  public void assignVar(String id, IValue value) throws InterpreterException {
    if (!(value instanceof VInt || value instanceof VBool))
      throw new InvalidVariableValueException(value);

    int memoryAddress = memory.addCell(value);

    environment.assign(id, new VCell(memoryAddress));
  }

  public void applyVar(String id, IValue value) throws InterpreterException {
    IValue oldValue = environment.find(id);

    if (oldValue instanceof VCell) {
      int oldValueAddress = ((VCell) oldValue).getAddress();

      memory.removeCellReference(oldValueAddress);
    }

    int memoryAddress;

    if (value instanceof VCell) {
      memoryAddress = ((VCell) value).getAddress();

      memory.addCellReference(memoryAddress);
    } else
      memoryAddress = memory.addCell(value);

    environment.assign(id, new VCell(memoryAddress));
  }

  public IValue find(String id) throws InterpreterException {
    IValue value = environment.find(id);

    if (value == null)
      throw new UndefinedVariableException(id);

    if (value instanceof VCell)
      value = memory.getCellValue(((VCell) value).getAddress());

    return value;
  }
}

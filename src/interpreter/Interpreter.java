package interpreter;

import errors.interpreter.*;
import value.IValue;
import value.VCell;

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

  public void assign(String id, IValue value) throws InterpreterException {
    if (!(value instanceof VCell))
      throw new UnexpectedTypeException(value.typeToString(), "cell");

      environment.assign(id, ((VCell) value));
  }

  public void apply(IValue ref, IValue newValue) throws InterpreterException {
    if (!(ref instanceof VCell))
      throw new UnexpectedTypeException(ref.typeToString(), "cell");

    memory.changeCellValue(((VCell) ref).getAddress(), newValue);
  }

  public IValue deref(String id) throws InterpreterException {
    VCell value = environment.find(id);

    if (value == null)
      throw new UndefinedVariableException(id);

    return memory.getCellValue(value.getAddress());
  }

  public VCell init(IValue value) throws InterpreterException {
    if (value instanceof VCell)
      throw new UnexpectedTypeException(value.typeToString(), "bool or int");

    int address = memory.addCell(value);

    return new VCell(address);
  }

  public VCell find(String id) throws InterpreterException {
    VCell value = environment.find(id);

    if (value == null)
      throw new UndefinedVariableException(id);

    return value;
  }
}

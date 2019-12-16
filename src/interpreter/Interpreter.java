package interpreter;

import interpreter.errors.CellNotFoundError;
import interpreter.errors.InterpretationError;
import interpreter.errors.InterpreterUndefinedVariableError;
import interpreter.errors.UnexpectedTypeError;
import values.IValue;
import values.VCell;

import java.util.HashSet;

public class Interpreter {
  private Environment environment;
  private Memory memory;

  public Interpreter() {
    environment = new Environment();
    memory = new Memory();
  }

  public void beginEnvScope() {
    environment.beginScope();
  }

  public void endEnvScope() throws CellNotFoundError {
    HashSet<VCell> cells = environment.currentScopeCells();

    for (VCell cell : cells) {
      memory.removeCellReference(cell.get());
    }

    environment.endScope();
  }

  public void assign(String id, IValue value) throws InterpretationError {
    if (!(value instanceof VCell))
      throw new UnexpectedTypeError(value.type(), "cell");

    environment.assign(id, ((VCell) value));
  }

  public void apply(IValue ref, IValue newValue) throws InterpretationError {
    if (!(ref instanceof VCell))
      throw new UnexpectedTypeError(ref.type(), "cell");

    memory.changeCellValue(((VCell) ref).get(), newValue);
  }

  public IValue deref(String id) throws InterpretationError {
    VCell value = environment.find(id);

    if (value == null)
      throw new InterpreterUndefinedVariableError(id);

    return memory.getCellValue(value.get());
  }

  public VCell init(IValue value) throws InterpretationError {
    if (value instanceof VCell)
      throw new UnexpectedTypeError(value.type(), "bool or int");

    int address = memory.addCell(value);

    return new VCell(address);
  }

  public VCell find(String id) throws InterpretationError {
    VCell value = environment.find(id);

    if (value == null)
      throw new InterpreterUndefinedVariableError(id);

    return value;
  }
}

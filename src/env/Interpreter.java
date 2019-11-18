package env;

import errors.interpreter.InterpreterException;
import errors.interpreter.InvalidConstantValueException;
import errors.interpreter.InvalidVariableValueException;
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
  }

  public void beginEnvScope() {
    environment.beginScope();
  }

  public void endEnvScope() {
    HashSet<VCell> cells = environment.currentScopeCells();

    for (VCell cell : cells) {
      memory.removeCellReference(cell.getAddress());
    }

    environment.endScope();
  }

  public void newConst(String id, IValue value) throws InterpreterException {
    if (!(value instanceof VInt || value instanceof VBool))
      throw new InvalidConstantValueException(value);

    environment.associate(id, value);
  }

  public void newVar(String id, IValue value) throws InterpreterException {
    if (!(value instanceof VInt || value instanceof VBool))
      throw new InvalidVariableValueException(value);

    int memoryAddress = memory.addCell(value);

    environment.associate(id, new VCell(memoryAddress));
  }

  public IValue find(String id) throws InterpreterException {
    IValue value = environment.find(id);

    if (value instanceof VCell)
      value = memory.getCellValue(((VCell) value).getAddress());

    return value;
  }
}

package interpreter;

import interpreter.errors.InterpretationError;
import values.IValue;
import values.VCell;

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
    environment.endScope();
  }

  public void assign(String id, IValue value) throws InterpretationError {
    if (!(value instanceof VCell))
      throw new InterpretationError("Unexpected type assigned to variable " + id, "assign value to variable");

    environment.assign(id, ((VCell) value));
  }

  public void apply(IValue ref, IValue newValue) throws InterpretationError {
    if (!(ref instanceof VCell))
      throw new InterpretationError("Unexpected type assigned to variable", "apply value to variable", ref.type());

    memory.changeCellValue(((VCell) ref).get(), newValue);
  }

  public IValue deref(String id) throws InterpretationError {
    VCell value = environment.find(id);

    return memory.getCellValue(value.get());
  }

  public VCell init(IValue value) throws InterpretationError {
    if (value instanceof VCell)
      throw new InterpretationError("Unexpected type", "initialize variable", value.type());

    int address = memory.addCell(value);

    return new VCell(address);
  }

  public VCell find(String id) throws InterpretationError {
    return environment.find(id);
  }
}

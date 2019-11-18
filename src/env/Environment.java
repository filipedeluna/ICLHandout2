package env;

import errors.interpreter.InterpreterException;
import errors.interpreter.OutsideOfScopeException;
import errors.interpreter.UndefinedVariableException;
import errors.interpreter.VariableAlreadyDefinedException;
import value.IValue;
import value.VCell;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

final class Environment {
  private ArrayDeque<HashMap<String, IValue>> scopes;

  Environment() {
    this.scopes = new ArrayDeque<>();
  }

  void beginScope() {
    scopes.push(new HashMap<>());
  }

  public void endScope() {
    scopes.pop();
  }

  void associate(String id, IValue value) throws InterpreterException {
    if (scopes.size() == 0 || scopes.peek() == null)
      throw new OutsideOfScopeException(id);

    HashMap<String, IValue> scope = scopes.peek();

    if (scope.get(id) != null)
      throw new VariableAlreadyDefinedException(id);

    scope.put(id, value);
  }

  IValue find(String id) throws InterpreterException {
    for (HashMap<String, IValue> scope : scopes) {
      if (scope.get(id) != null)
        return scope.get(id);
    }
    throw new UndefinedVariableException(id);
  }

  // Return all the values from current scope whose
  // values represent cells
  HashSet<VCell> currentScopeCells() {
    HashSet<VCell> scopeCells = new HashSet<>();

    if (scopes.peek() != null)
      for (Entry<String, IValue> entry : scopes.peek().entrySet()) {
        if (entry.getValue() instanceof VCell)
          scopeCells.add((VCell) entry.getValue());
      }

    return scopeCells;
  }
}

package interpreter;

import interpreter.errors.InterpretationError;
import values.VCell;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

final class Environment {
  private ArrayDeque<HashMap<String, VCell>> scopes;

  Environment() {
    this.scopes = new ArrayDeque<>();
  }

  void beginScope() {
    scopes.push(new HashMap<>());
  }

  void endScope() {
    scopes.pop();
  }

  void assign(String id, VCell cell) throws InterpretationError {
    if (scopes.size() == 0 || scopes.peek() == null)
      throw new InterpretationError("Outside of scope", "variable assignment");

    HashMap<String, VCell> scope = scopes.peek();

    if (scope.get(id) != null)
      throw new InterpretationError("Variable " + id + " already assigned", "variable assignment");

    scope.put(id, cell);
  }

  VCell find(String id) throws InterpretationError {
    for (HashMap<String, VCell> scope : scopes) {
      if (scope.get(id) != null)
        return scope.get(id);
    }

    throw new InterpretationError("Variable " + id + " is not defined", "variable lookup");
  }

  // Return all the values from current scope whose
  // values represent cells
  HashSet<VCell> currentScopeCells() {
    HashSet<VCell> scopeCells = new HashSet<>();

    if (scopes.peek() != null)
      for (Entry<String, VCell> entry : scopes.peek().entrySet()) {
        scopeCells.add(entry.getValue());
      }

    return scopeCells;
  }
}

package typechecker;

import typechecker.errors.TypeCheckError;
import types.IType;

import java.util.ArrayDeque;
import java.util.HashMap;

final class TypeEnvironment {
  private ArrayDeque<HashMap<String, IType>> scopes;

  TypeEnvironment() {
    this.scopes = new ArrayDeque<>();
  }

  void beginScope() {
    scopes.push(new HashMap<>());
  }

  void endScope() {
    scopes.pop();
  }

  void assign(String id, IType type) throws TypeCheckError {
    if (scopes.size() == 0 || scopes.peek() == null)
      throw new TypeCheckError("Outside of scope", "assign " + id + " to scope");

    HashMap<String, IType> scope = scopes.peek();

    if (scope.get(id) != null)
      throw new TypeCheckError("Variable " + id + "already defined", "assign");

    scope.put(id, type);
  }

  IType find(String id) throws TypeCheckError {
    for (HashMap<String, IType> scope : scopes) {
      if (scope.get(id) != null)
        return scope.get(id);
    }

    throw new TypeCheckError("Variable " + id + "not found", "variable find");
  }
}

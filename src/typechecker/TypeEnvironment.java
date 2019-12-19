package typechecker;

import typechecker.errors.TypeCheckError;
import types.IType;

import java.util.ArrayDeque;
import java.util.HashMap;

final class TypeEnvironment {
  private ArrayDeque<HashMap<String, IType>> scopes;

  private IType tempType;

  TypeEnvironment() {
    this.scopes = new ArrayDeque<>();
  }

  void beginScope() {
    scopes.push(new HashMap<>());
  }

  void endScope() {
    scopes.pop();
  }

  void loadTempType(IType type) throws TypeCheckError {
    if (tempType == null)
      throw new TypeCheckError("Temporary type not loaded", "type initialization");

    tempType = type;
  }

  boolean compareTempType(IType type) throws TypeCheckError {


    return tempType.equals(type);
  }

  void assignTempType(String id, IType type) throws TypeCheckError {
    if (tempType == null)
      throw new TypeCheckError("Temporary type not loaded", "assign " + id + " to scope");

    if (scopes.size() == 0 || scopes.peek() == null)
      throw new TypeCheckError("Outside of scope", "assign " + id + " to scope");

    HashMap<String, IType> scope = scopes.peek();

    if (scope.get(id) != null)
      throw new TypeCheckError("Variable " + id + "already defined", "assign " + id + " to scope");

    if (!tempType.equals(type))
      throw new TypeCheckError("Types do not match", "assign " + id + " to scope");

    scope.put(id, tempType);

    tempType = null;
  }

  void assign(String id, IType type) throws TypeCheckError {
    if (scopes.size() == 0 || scopes.peek() == null)
      throw new TypeCheckError("Outside of scope", "assign " + id + " to scope");

    HashMap<String, IType> scope = scopes.peek();

    if (scope.get(id) != null)
      throw new TypeCheckError("Variable " + id + "already defined", "assign");

    scope.put(id, type);
  }

  void overwrite(String id, IType type) throws TypeCheckError {
    if (scopes.size() == 0 || scopes.peek() == null)
      throw new TypeCheckError("Outside of scope", "assign " + id + " to scope");

    HashMap<String, IType> scope = scopes.peek();

    if (scope.get(id) == null)
      throw new TypeCheckError("Variable " + id + "not found", "variable find");

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

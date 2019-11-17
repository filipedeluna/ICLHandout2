package env;

import errors.env.EnvironmentException;
import errors.env.OutsideOfScopeException;
import errors.env.UndefinedVariableException;
import errors.env.VariableAlreadyDefinedException;
import value.IValue;

import java.util.ArrayDeque;
import java.util.HashMap;

public class Environment {
  private ArrayDeque<HashMap<String, Integer>> scopes;

  public Environment() {
    this.scopes = new ArrayDeque<>();
  }

  public void beginScope() {
    scopes.push(new HashMap<>());
  }

  public void endScope() {
    scopes.pop();
  }

  public void associate(String id, IValue value) throws EnvironmentException {
    if (scopes.size() == 0 || scopes.peek() == null)
      throw new OutsideOfScopeException(id);

    HashMap<String, IValue> scope = scopes.peek();

    if (scope.get(id) != null)
      throw new VariableAlreadyDefinedException(id);

    scope.put(id, value);
  }

  public IValue find(String id) throws EnvironmentException {
    for (HashMap<String, Integer> scope : scopes) {
      if (scope.get(id) != null)
        return scope.get(id);
    }
    throw new UndefinedVariableException(id);
  }
}

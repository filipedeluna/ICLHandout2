package typechecker;

import typechecker.errors.TypeCheckError;
import types.IType;

public class TypeChecker {
  private TypeEnvironment environment;

  public TypeChecker() {
    environment = new TypeEnvironment();
  }

  public void beginEnvScope() {
    environment.beginScope();
  }

  public void endEnvScope() {
    environment.endScope();
  }

  public void assign(String id, IType type) throws TypeCheckError {
    environment.assign(id, type);
  }

  public IType find(String id) throws TypeCheckError {
    return environment.find(id);
  }
}

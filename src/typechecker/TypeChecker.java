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

  public void overwrite(String id, IType type) throws TypeCheckError {
    environment.overwrite(id, type);
  }

  public void assignTempType(String id, IType type) throws TypeCheckError {
    environment.assignTempType(id, type);
  }

  public void loadTempType(IType type) throws TypeCheckError {
    environment.loadTempType(type);
  }

  public IType find(String id) throws TypeCheckError {
    return environment.find(id);
  }
}

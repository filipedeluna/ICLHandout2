package node.types;

import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import node.ASTNode;
import value.IValue;

public class ASTVar implements ASTNode {
  private String id;

  public ASTVar(String id) {
    this.id = id;
  }

  @Override
  public IValue eval(Environment env) throws EnvironmentException {
    return env.find(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.getFieldFromFrame(id);
  }
}

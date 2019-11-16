package node.types;

import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import node.ASTNode;

public class ASTId implements ASTNode {
  private String id;

  public ASTId(String id) {
    this.id = id;
  }

  @Override
  public int eval(Environment env) throws EnvironmentException {
    return env.find(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.getFieldFromFrame(id);
  }
}

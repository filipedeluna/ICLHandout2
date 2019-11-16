package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import node.ASTNode;

public class ASTNum implements ASTNode {
  private int val;

  public ASTNum(int val) {
    this.val = val;
  }

  @Override
  public int eval(Environment env) {
    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.emit(ByteCode.PUSH, String.valueOf(val));
  }
}

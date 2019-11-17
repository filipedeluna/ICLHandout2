package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.eval.EvaluationException;
import errors.eval.UnexpectedTypeException;
import node.ASTNode;
import value.IValue;
import value.VBool;

public class ASTBool implements ASTNode {
  private IValue val;

  public ASTBool(IValue val) throws EvaluationException {
    if (!(val instanceof VBool))
      throw new UnexpectedTypeException(val.type(), "bool");

    this.val = val;
  }

  @Override
  public IValue eval(Environment env) {
    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    boolean b = ((VBool) val).get();

    compiler.emit(ByteCode.PUSH, b ? "1" : "0");
  }
}

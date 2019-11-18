package node.relational;

import compiler.Compiler;
import env.Interpreter;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public final class ASTEq extends ASTRelational {
  public ASTEq(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return eval(RelationalOperation.EQUALS, interpreter);
  }

  @Override
  public void compile(Compiler compiler) {

  }
}

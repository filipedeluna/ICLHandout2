package node.relational;

import compiler.Compiler;
import env.Interpreter;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public final class ASTLowerOrEq extends ASTRelational {
  public ASTLowerOrEq(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return eval(RelationalOperation.LOWER_OR_EQUALS, interpreter);
  }

  @Override
  public void compile(Compiler compiler) {

  }
}

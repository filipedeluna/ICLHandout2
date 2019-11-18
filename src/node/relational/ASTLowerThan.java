package node.relational;

import compiler.Compiler;
import env.Interpreter;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public final class ASTLowerThan extends ASTRelational {
  public ASTLowerThan(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return eval(RelationalOperation.LOWER_THAN, interpreter);
  }

  @Override
  public void compile(Compiler compiler) {

  }
}

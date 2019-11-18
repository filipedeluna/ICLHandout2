package node.logical;

import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public final class ASTAnd extends ASTLogical {
  public ASTAnd(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return eval(LogicalOperation.AND, interpreter);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compile(LogicalOperation.AND, compiler);
  }
}

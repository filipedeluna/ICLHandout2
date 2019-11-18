package node.logical;

import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTComp extends ASTLogical {
  public ASTComp(ASTNode left) {
    super(left);
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return eval(LogicalOperation.COMP, interpreter);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compile(LogicalOperation.COMP, compiler);
  }
}

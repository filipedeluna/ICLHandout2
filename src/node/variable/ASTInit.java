package node.variable;

import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTInit implements ASTNode {
  private ASTNode value;

  public ASTInit(ASTNode value) {
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue iv = value.eval(interpreter);

    return interpreter.initCell(iv);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.loadStaticLink();
    // TODO
    value.compile(compiler);
  }
}

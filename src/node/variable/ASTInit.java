package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTInit implements ASTNode {
  private IValue value;

  public ASTInit(IValue value) {
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return interpreter.initCell(value);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.loadStaticLink();
    // TODO
    // value.compile(compiler);
  }
}

package node.block;

import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTLet implements ASTNode {
  private ASTNode node;

  public ASTLet(ASTNode node) {
    this.node = node;
  }

  public IValue eval(Interpreter interpreter) throws InterpreterException {
    interpreter.beginEnvScope();

    IValue val = node.eval(interpreter);

    interpreter.endEnvScope();

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.beginFrame();

    node.compile(compiler);

    compiler.endFrame();
  }
}

package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTAssign implements ASTNode {
  private String id;
  private ASTNode value;

  public ASTAssign(String id, ASTNode value) {
    this.id = id;
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue iv = value.eval(interpreter);

    interpreter.assign(id, iv);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.loadStaticLink();

    value.compile(compiler);

    compiler.addFrameField(id);
  }
}

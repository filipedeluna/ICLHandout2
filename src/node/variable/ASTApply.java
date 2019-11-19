package node.variable;

import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTApply implements ASTNode {
  private String id;
  private ASTNode value;

  public ASTApply(String id, ASTNode value) {
    this.id = id;
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue iv = value.eval(interpreter);

    interpreter.applyVar(id, iv);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.loadStaticLink();

    value.compile(compiler);

    compiler.addFieldToFrame(id);
  }
}

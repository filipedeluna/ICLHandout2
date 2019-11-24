package node.types;

import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public final class ASTVar implements ASTNode {
  private String id;

  public ASTVar(String id) {
    this.id = id;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return interpreter.find(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.pushFrameField(id);
  }
}

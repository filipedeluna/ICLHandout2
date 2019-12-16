package node.types;

import compiler.Compiler;
import compiler.errors.CompilerError;
import interpreter.Interpreter;
import interpreter.errors.InterpreterError;
import node.ASTNode;
import values.IValue;

public final class ASTString implements ASTNode {
  private ArrayList<> id;
  private String id;

  public ASTString(IValue val) {
    this.id = id;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    return interpreter.find(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    compiler.pushFrameField(id);
  }
}

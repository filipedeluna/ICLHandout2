package node.types;

import compiler.Compiler;
import compiler.errors.CompilerError;
import interpreter.errors.InterpreterError;
import interpreter.Interpreter;
import node.ASTNode;
import types.IType;
import values.IValue;

public final class ASTFun implements ASTNode {
  private ArrayList<> id;
  private String id;

  public ASTFun(IType type, ASTNode block) {
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

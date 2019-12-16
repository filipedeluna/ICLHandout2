package node.types;

import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
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
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    return interpreter.find(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.pushFrameField(id);
  }
}

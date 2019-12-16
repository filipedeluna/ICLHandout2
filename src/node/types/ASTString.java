package node.types;

import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import values.IValue;

public final class ASTString implements ASTNode {
  private ArrayList<> id;
  private String id;

  public ASTString(IValue val) {
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

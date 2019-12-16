package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.errors.InterpretationError;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.UnexpectedTypeError;
import node.ASTNode;
import values.IValue;
import values.VInt;

public final class ASTNum implements ASTNode {
  private IValue val;

  public ASTNum(IValue val) {
    this.val = val;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    if (!(val instanceof VInt))
      throw new UnexpectedTypeError(val.type().toString(), "int");

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.emit(ByteCode.PUSH, String.valueOf(val));
  }
}

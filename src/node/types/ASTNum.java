package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.errors.InterpreterError;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
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
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    if (!(val instanceof VInt))
      throw new UnexpectedTypeError(val.type().toString(), "int");

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    compiler.emit(ByteCode.PUSH, String.valueOf(val));
  }
}

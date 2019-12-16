package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
import interpreter.errors.InterpreterError;
import interpreter.errors.UnexpectedTypeError;
import node.ASTNode;
import values.IValue;
import values.VBool;

public final class ASTBool implements ASTNode {
  private IValue val;

  public ASTBool(IValue val) {
    this.val = val;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    if (!(val instanceof VBool))
      throw new UnexpectedTypeError(val.type().toString(), "bool");

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    boolean b = ((VBool) val).get();

    compiler.emit(ByteCode.PUSH, b ? "1" : "0");
  }
}

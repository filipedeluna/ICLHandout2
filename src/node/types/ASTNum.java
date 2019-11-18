package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import errors.interpreter.UnexpectedTypeException;
import node.ASTNode;
import value.IValue;
import value.VInt;

public final class ASTNum implements ASTNode {
  private IValue val;

  public ASTNum(IValue val) throws InterpreterException {
    if (!(val instanceof VInt))
      throw new UnexpectedTypeException(val.typeToString(), "int");

    this.val = val;
  }

  @Override
  public IValue eval(Interpreter interpreter) {
    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.emit(ByteCode.PUSH, String.valueOf(val));
  }
}

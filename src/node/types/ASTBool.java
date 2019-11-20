package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import errors.interpreter.UnexpectedTypeException;
import node.ASTNode;
import value.IValue;
import value.VBool;

public final class ASTBool implements ASTNode {
  private IValue val;

  public ASTBool(IValue val) {
    this.val = val;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    if (!(val instanceof VBool))
      throw new UnexpectedTypeException(val.typeToString(), "bool");

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    boolean b = ((VBool) val).get();

    compiler.emit(ByteCode.PUSH, b ? "1" : "0");
  }
}

package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import compiler.errors.CompilerError;
import interpreter.Interpreter;
import interpreter.errors.InterpreterError;
import interpreter.errors.UnexpectedTypeError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;
import values.VInt;

public final class ASTStructDeref implements ASTNode {
  private IValue val;

  public ASTStructDeref(String structId, String fieldId) {
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

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return null;
  }
}

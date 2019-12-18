package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.errors.InterpretationError;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TInt;
import values.IValue;
import values.VInt;

public final class ASTInt implements ASTNode {
  private IValue val;

  public ASTInt(IValue val) {
    this.val = val;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    if (!(val instanceof VInt))
      throw new InterpretationError("Unexpected value type", "cast to int", val);

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.emit(ByteCode.PUSH, String.valueOf(val));
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    if (!(val.type() instanceof TInt))
      throw new TypeCheckError("Unexpected type", "cast to int", val.type());

    return TInt.SINGLETON;
  }
}

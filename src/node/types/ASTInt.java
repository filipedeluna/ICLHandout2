package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import compiler.CompilerType;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TInt;
import values.IValue;

public final class ASTInt implements ASTNode {
  private IValue val;

  public ASTInt(IValue val) {
    this.val = val;
  }

  @Override
  public IValue eval(Interpreter interpreter) {
    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.emit(ByteCode.PUSH, val.asString());

    compiler.cache.setType(CompilerType.INT);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    if (!(val.type() instanceof TInt))
      throw new TypeCheckError("Unexpected type", "cast to int", val.type());

    return TInt.SINGLETON;
  }
}

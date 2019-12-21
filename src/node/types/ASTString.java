package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import compiler.CompilerType;
import compiler.cache.CacheEntry;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TString;
import values.IValue;

public final class ASTString implements ASTNode {
  private IValue val;

  public ASTString(IValue val) {
    this.val = val;
  }

  @Override
  public IValue eval(Interpreter interpreter) {
    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.emit(ByteCode.LOAD_C, '"' + val.asString() + "'");

    compiler.cache.push(new CacheEntry(CompilerType.STRING));
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    if (!(val.type() instanceof TString))
      throw new TypeCheckError("Unexpected type", "cast to int", val.type());

    return TString.SINGLETON;
  }
}

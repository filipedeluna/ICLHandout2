package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TInt;
import types.TString;
import values.IValue;
import values.VString;

public final class ASTString implements ASTNode {
  private IValue val;

  public ASTString(IValue val) {
    this.val = val;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    if (!(val instanceof VString))
      throw new InterpretationError("Unexpected value type", "cast to string", val);

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    // TODO string files creation
    compiler.emit(ByteCode.PUSH, String.valueOf(val));
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    if (!(val.type() instanceof TString))
      throw new TypeCheckError("Unexpected type", "cast to int", val.type());

    return TString.SINGLETON;
  }
}

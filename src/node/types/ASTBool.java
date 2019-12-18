package node.types;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TBool;
import values.IValue;
import values.VBool;

public final class ASTBool implements ASTNode {
  private IValue val;

  public ASTBool(IValue val) {
    this.val = val;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    if (!(val instanceof VBool))
      throw new InterpretationError("Unexpected value type", "cast to bool", val.type());

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    boolean b = ((VBool) val).get();

    compiler.emit(ByteCode.PUSH, b ? "1" : "0");
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    if (!(val.type() instanceof TBool))
      throw new TypeCheckError("Unexpected type", "cast to bool", val.type());

    return TBool.SINGLETON;
  }
}

package node.initialization;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TCell;
import types.TFun;
import types.TStruct;
import values.*;

public class ASTInit implements ASTNode {
  private IValue value;

  public ASTInit(IValue value) {
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    return interpreter.init(value);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    if (value instanceof VInt || value instanceof VBool) {
      compiler.emit(ByteCode.PUSH, value.asString());
      return;
    }

    if (value instanceof VString) {
      compiler.emit(ByteCode.LOAD_C, "\"" + value.asString() + "\"");
      return;
    }

    compiler.pushTempValue(value);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    if (value.type() instanceof TFun || value.type() instanceof TCell || value.type() instanceof TStruct)
      throw new TypeCheckError("Invalid type", "variable initialization", value.type());

    typeChecker.loadTempType(value.type());

    return TCell.SINGLETON;
  }
}

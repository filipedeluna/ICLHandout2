package node.initialization;

import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TCell;
import types.TStruct;
import values.*;

public class ASTStructInit implements ASTNode {
  private ASTNode node;

  public ASTStructInit(ASTNode node) {
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue value = node.eval(interpreter);

    return interpreter.init(value);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    /*
    if (value instanceof VInt || value instanceof VBool) {
      compiler.emit(ByteCode.PUSH, value.asString());
      return;
    }

    if (value instanceof VString) {
      compiler.emit(ByteCode.LOAD_C, "\"" + value.asString() + "\"");
      return;
    }

    compiler.pushTempValue(value);

    throw new CompileError("Invalid value type", "variable initialization");
    */
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type = node.typeCheck(typeChecker);

    if (!(type instanceof TStruct))
      throw new TypeCheckError("Invalid type, expected struct", "struct variable initialization", type);

    typeChecker.loadTempType(type);

    return TCell.SINGLETON;
  }
}

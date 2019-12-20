package node.variable;

import compiler.ByteCode;
import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TCell;
import types.TFun;
import types.TStruct;
import values.*;

public class ASTVarInit implements ASTNode {
  private String var;

  public ASTVarInit(String var) {
    this.var = var;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    return interpreter.find(var);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {

  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType varType = typeChecker.find(var);

    if (varType instanceof TFun || varType instanceof TCell || varType instanceof TStruct)
      throw new TypeCheckError("Invalid type", "variable initialization", varType);

    typeChecker.loadTempType(varType);

    return TCell.SINGLETON;
  }
}

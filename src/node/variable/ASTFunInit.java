package node.variable;

import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import node.types.FunParam;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.*;
import values.IValue;
import values.VFun;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ASTFunInit implements ASTNode {
  private ArrayList<FunParam> funParams;
  private ASTNode block;

  public ASTFunInit(ArrayList<FunParam> funParams, ASTNode block) {
    this.funParams = funParams;
    this.block = block;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    LinkedHashMap<String, IType> params = new LinkedHashMap<>();

    for (FunParam funParam : funParams) {
      if (params.containsKey(funParam.getId()))
        throw new InterpretationError("Duplicate parameter name: " + funParam.getId(), "function initialization");

      params.put(funParam.getId(), funParam.getType());
    }

    VFun function = new VFun(params, block);

    return interpreter.init(function);
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
    IType returnType = block.typeCheck(typeChecker);

    ArrayList<IType> paramTypes = new ArrayList<>();

    IType paramType;
    for (FunParam funParam : funParams) {
      paramType = funParam.getType();

      if (!(paramType instanceof TInt) && !(paramType instanceof TBool) && !(paramType instanceof TString) && !(paramType instanceof TStruct))
        throw new TypeCheckError("Invalid function parameter type", "function initialization");

      paramTypes.add(paramType);
    }

    typeChecker.loadTempType(new TFun(paramTypes, returnType));

    return TCell.SINGLETON;
  }
}

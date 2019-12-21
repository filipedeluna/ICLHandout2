package node.initialization;

import compiler.Compiler;
import compiler.CompilerType;
import compiler.cache.CacheEntry;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import node.variable.FunParam;
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

    for (FunParam funParam : funParams)
      params.put(funParam.getId(), funParam.getType());

    VFun function = new VFun(params, block);

    return interpreter.init(function);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    LinkedHashMap<String, CompilerType> params = new LinkedHashMap<>();

    for (FunParam funParam : funParams) {
      if (funParam.getType() instanceof TString)
        params.put(funParam.getId(), CompilerType.STRING);
      if (funParam.getType() instanceof TBool)
        params.put(funParam.getId(), CompilerType.BOOL);
      if (funParam.getType() instanceof TInt)
        params.put(funParam.getId(), CompilerType.INT);
    }

    compiler.cache.push(new CacheEntry(params, block));
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    ArrayList<IType> paramTypes = new ArrayList<>();
    ArrayList<String> paramNames = new ArrayList<>();

    IType paramType;
    for (FunParam funParam : funParams) {
      paramType = funParam.getType();

      if (paramNames.contains(funParam.getId()))
        throw new TypeCheckError("Duplicate parameter name: " + funParam.getId(), "function initialization");

      if (!(paramType instanceof TInt) && !(paramType instanceof TBool) && !(paramType instanceof TString))
        throw new TypeCheckError("Invalid function parameter type", "function initialization");

      paramNames.add(funParam.getId());
      paramTypes.add(paramType);
    }

    // Create dummy vars for type checking-------------

    typeChecker.beginEnvScope();

    for (FunParam funParam : funParams)
      typeChecker.assign(funParam.getId(), funParam.getType());

    // ------------------------------------------------

    IType returnType = block.typeCheck(typeChecker);

    typeChecker.endEnvScope();

    typeChecker.loadTempType(new TFun(paramTypes, returnType));

    return TCell.SINGLETON;
  }
}

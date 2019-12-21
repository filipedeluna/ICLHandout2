package node.variable;

import compiler.Compiler;
import compiler.CompilerType;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.*;
import values.*;

import java.util.ArrayList;
import java.util.Map.Entry;

public class ASTAssign implements ASTNode {
  private String id;
  private ASTNode value;
  private IType type;

  public ASTAssign(String id, IType type, ASTNode value) {
    this.id = id;
    this.value = value;
    this.type = type;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue iv = value.eval(interpreter);

    interpreter.assign(id, iv);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    value.compile(compiler);

    CompilerType cType = compiler.cache.getType();

    if (cType.isLit() || cType == CompilerType.STRUCT) {
      compiler.addFrameField(id, cType);

      if (cType == CompilerType.STRUCT) {
        for (Entry<String, CompilerType> structParam : compiler.cache.getStructParams().entrySet())
          compiler.addFieldToFrameStructField(id, structParam.getKey(), structParam.getValue());
      }
    }

    if (cType == CompilerType.FUN) {
      ArrayList<CompilerType> funParams = compiler.cache.getFunParams();

      CompilerType returnType = null;

      if (((TFun) type).getReturnType() instanceof TInt)
        returnType = CompilerType.INT;

      if (((TFun) type).getReturnType() instanceof TBool)
        returnType = CompilerType.BOOL;

      if (((TFun) type).getReturnType() instanceof TString)
        returnType = CompilerType.STRING;

      ASTNode funBlock = compiler.cache.getFunBlock();

      compiler.addFrameFunctionField(id, funBlock, funParams, returnType);
    }
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType cellType = value.typeCheck(typeChecker);

    if (!(cellType instanceof TCell))
      throw new TypeCheckError("Expected a reference to a value", "assign");

    typeChecker.assignTempType(id, type);

    return TVoid.SINGLETON;
  }
}

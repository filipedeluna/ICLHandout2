package node.types;

import compiler.Compiler;
import compiler.CompilerType;
import compiler.cache.CacheEntry;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.*;
import values.IValue;
import values.VStruct;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public final class ASTStruct implements ASTNode {
  private ArrayList<ASTStructParam> structParams;

  public ASTStruct(ArrayList<ASTStructParam> structParams) {
    this.structParams = structParams;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue paramVal;

    VStruct struct = new VStruct();

    for (ASTStructParam structParam : structParams) {
      paramVal = structParam.eval(interpreter);

      struct.put(structParam.getId(), paramVal);
    }

    return struct;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    CompilerType type;
    String id;

    LinkedHashMap<String, CompilerType> frameParams = new LinkedHashMap<>();
    CacheEntry cacheEntry;

    for (ASTStructParam structParam : structParams) {
      structParam.compile(compiler);
      id = structParam.getId();

      cacheEntry = compiler.cache.pop();
      type = cacheEntry.getType();

      frameParams.put(id, type);
    }

    compiler.cache.push(new CacheEntry(frameParams));
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType paramType;

    TStruct struct = new TStruct();

    for (ASTStructParam structParam : structParams) {
      paramType = structParam.typeCheck(typeChecker);

      if (!(paramType instanceof TInt) && !(paramType instanceof TString) && !(paramType instanceof TBool))
        throw new TypeCheckError("Invalid struct parameter value", "struct creation");

      if (struct.contains(structParam.getId()))
        throw new TypeCheckError("Duplicate variable name in struct", "struct creation");

      struct.put(structParam.getId(), paramType);
    }

    return struct;
  }
}

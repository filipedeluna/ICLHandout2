package node.variable;

import compiler.Compiler;
import compiler.CompilerType;
import compiler.cache.CacheEntry;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TCell;
import values.IValue;

public final class ASTVar implements ASTNode {
  private String id;

  public ASTVar(String id) {
    this.id = id;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    return interpreter.find(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.cache.push(new CacheEntry(CompilerType.CELL, id));
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return new TCell(id);
  }
}

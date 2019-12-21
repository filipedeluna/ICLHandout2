package node.variable;

import compiler.Compiler;
import compiler.CompilerType;
import compiler.cache.CacheEntry;
import compiler.frame.FrameField;
import compiler.frame.FrameFunctionField;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;

public class ASTDeref implements ASTNode {
  private String id;

  public ASTDeref(String id) {
    this.id = id;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    return interpreter.deref(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    FrameField frameField = compiler.getFrameField(id);

    if (frameField.getType().isLit())
      compiler.cache.push(new CacheEntry(frameField.getType()));
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return typeChecker.find(id);
  }
}

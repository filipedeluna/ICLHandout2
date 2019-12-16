package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
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
    compiler.pushFrameField(id);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return null;
  }
}

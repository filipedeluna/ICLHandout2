package node.variable;

import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;

public class ASTRef implements ASTNode {
  private String id;

  public ASTRef(IType type, ASTNode value) {
    this.id = id;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    return interpreter.deref(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.getFrameField(id);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return null;
  }
}

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

public class ASTStruct implements ASTNode {
  private String id;
  private IType type;

  public ASTStruct(IValue value)  {
    this.id = id;

  }

  public String getId() {
    return id;
  }

  public IType getType() {
    return type;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {

  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return null;
  }
}

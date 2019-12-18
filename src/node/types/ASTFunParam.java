package node.types;

import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.*;
import values.IValue;

public class ASTFunParam implements ASTNode {
  private String id;
  private IType type;

  public ASTFunParam(String id, IType type) {
    this.id = id;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public IType getType() {
    return type;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    if (!(type instanceof TBool) && !(type instanceof TString) && !(type instanceof TInt))
      throw new InterpretationError("Unexpected value type", "cast to function parameter", type);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    if (!(type instanceof TBool) && !(type instanceof TString) && !(type instanceof TInt))
      throw new TypeCheckError("Unexpected type", "cast to function parameter", type);

    return TVoid.SINGLETON;
  }
}

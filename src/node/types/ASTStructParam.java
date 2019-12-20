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

public class ASTStructParam implements ASTNode {
  private String id;
  private ASTNode node;

  public ASTStructParam(String id, ASTNode node) {
    this.id = id;
    this.node = node;
  }

  public String getId() {
    return id;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    return node.eval(interpreter);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    node.compile(compiler);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type = node.typeCheck(typeChecker);

    if (!(type instanceof TBool) && !(type instanceof TString) && !(type instanceof TInt))
      throw new TypeCheckError("Unexpected type", "cast to struct parameter", type);

    return type;
  }
}

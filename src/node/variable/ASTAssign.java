package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TVoid;
import values.IValue;

public class ASTAssign implements ASTNode {
  private String id;
  private ASTNode value;

  public ASTAssign(String id, ASTNode value) {
    this.id = id;
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue iv = value.eval(interpreter);

    interpreter.assign(id, iv);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.loadStaticLink();

    value.compile(compiler);

    compiler.addFrameField(id);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type = value.typeCheck(typeChecker);

    if (type instanceof TVoid)
      throw new TypeCheckError("Value expected", "assign");

    typeChecker.assign(id, type);

    return TVoid.SINGLETON;
  }
}

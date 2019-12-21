package node.initialization;

import compiler.Compiler;
import compiler.CompilerType;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import node.variable.FunParam;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.*;
import values.*;

public class ASTStructInit implements ASTNode {
  private ASTNode node;

  public ASTStructInit(ASTNode node) {
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue value = node.eval(interpreter);

    return interpreter.init(value);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    node.compile(compiler);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type = node.typeCheck(typeChecker);

    if (!(type instanceof TStruct))
      throw new TypeCheckError("Invalid type, expected struct", "struct variable initialization", type);

    typeChecker.loadTempType(type);

    return TCell.SINGLETON;
  }
}

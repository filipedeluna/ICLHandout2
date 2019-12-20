package node.block;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;

public class ASTSeq implements ASTNode {
  private ASTNode statement;
  private ASTNode node;

  public ASTSeq(ASTNode statement, ASTNode node) {
    this.statement = statement;
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    statement.eval(interpreter);

    return node.eval(interpreter);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    statement.compile(compiler);

    node.compile(compiler);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    statement.typeCheck(typeChecker);

    return node.typeCheck(typeChecker);
  }
}

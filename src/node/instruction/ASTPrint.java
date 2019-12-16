package node.instruction;

import compiler.Compiler;
import compiler.errors.CompilerError;
import interpreter.Interpreter;
import interpreter.errors.InterpreterError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;

public class ASTPrint implements ASTNode {

  public ASTPrint(ASTNode node) {

  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {

  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return null;
  }
}

package node.variable;

import compiler.Compiler;
import compiler.errors.CompilerError;
import interpreter.Interpreter;
import interpreter.errors.InterpreterError;
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
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    return interpreter.deref(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    compiler.getFrameField(id);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return null;
  }
}

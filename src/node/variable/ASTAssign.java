package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
import interpreter.errors.InterpreterError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;

public class ASTAssign implements ASTNode {
  private String id;
  private ASTNode value;

  public ASTAssign(String id, ASTNode value) {
    this.id = id;
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    IValue iv = value.eval(interpreter);

    interpreter.assign(id, iv);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    compiler.loadStaticLink();

    value.compile(compiler);

    compiler.addFrameField(id);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return null;
  }
}

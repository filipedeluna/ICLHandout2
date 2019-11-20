package node.block;

import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTSeq implements ASTNode {
  private ASTNode statement;
  private ASTNode node;

  public ASTSeq(ASTNode statement, ASTNode node) {
    this.statement = statement;
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    statement.eval(interpreter);

    return node.eval(interpreter);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {

  }
}

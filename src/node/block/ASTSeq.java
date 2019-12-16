package node.block;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
import interpreter.errors.InterpreterError;
import node.ASTNode;
import values.IValue;

public class ASTSeq implements ASTNode {
  private ASTNode statement;
  private ASTNode node;

  public ASTSeq(ASTNode statement, ASTNode node) {
    this.statement = statement;
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    statement.eval(interpreter);

    return node.eval(interpreter);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    statement.compile(compiler);

    node.compile(compiler);
  }
}

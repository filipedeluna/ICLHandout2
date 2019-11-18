package node.block;

import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

import java.util.HashSet;

public class ASTBlock implements ASTNode {
  private HashSet<ASTAssignVar> assignments;
  private ASTNode block;

  public ASTBlock(HashSet<ASTAssignVar> assignments, ASTNode block) {
    this.assignments = assignments;
    this.block = block;
  }

  public ASTBlock(ASTNode block) {
    this.block = block;
  }

  public IValue eval(Interpreter interpreter) throws InterpreterException {
    interpreter.beginEnvScope();

    if (assignments != null)
      for (ASTAssignVar assignment : assignments)
        assignment.eval(interpreter);

    IValue val = block.eval(interpreter);

    interpreter.endEnvScope();

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.beginFrame();

    if (assignments != null)
      for (ASTAssignVar assignment : assignments)
        assignment.compile(compiler);

    block.compile(compiler);

    compiler.endFrame();
  }
}

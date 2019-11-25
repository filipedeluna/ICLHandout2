package node.block;

import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import node.variable.ASTAssign;
import value.IValue;

import java.util.HashSet;

public class ASTLet implements ASTNode {
  private HashSet<ASTAssign> assignments;
  private ASTNode node;

  public ASTLet(ASTNode node, HashSet<ASTAssign> assignments) {
    this.node = node;
    this.assignments = assignments;
  }

  public IValue eval(Interpreter interpreter) throws InterpreterException {
    interpreter.beginEnvScope();

    for (ASTAssign assignment : assignments) {
      assignment.eval(interpreter);
    }

    IValue val = node.eval(interpreter);

    interpreter.endEnvScope();

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.beginFrame();

    for (ASTAssign assignment : assignments) {
      assignment.compile(compiler);
    }

    node.compile(compiler);

    compiler.endFrame();
  }
}

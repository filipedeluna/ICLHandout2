package node.block;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
import interpreter.errors.InterpreterError;
import node.ASTNode;
import node.variable.ASTAssign;
import values.IValue;

import java.util.ArrayList;

public class ASTLet implements ASTNode {
  private ArrayList<ASTAssign> assignments;
  private ASTNode node;

  public ASTLet(ASTNode node, ArrayList<ASTAssign> assignments) {
    this.node = node;
    this.assignments = assignments;
  }

  public IValue eval(Interpreter interpreter) throws InterpreterError {
    interpreter.beginEnvScope();

    for (ASTAssign assignment : assignments) {
      assignment.eval(interpreter);
    }

    IValue val = node.eval(interpreter);

    interpreter.endEnvScope();

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    compiler.beginFrame();

    for (ASTAssign assignment : assignments) {
      assignment.compile(compiler);
    }

    node.compile(compiler);

    compiler.endFrame();
  }
}

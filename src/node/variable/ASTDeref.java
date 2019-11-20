package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTDeref implements ASTNode {
  private String id;

  public ASTDeref(String id) {
    this.id = id;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return interpreter.find(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.loadStaticLink();

    // TODO

    compiler.addFieldToFrame(id);
  }
}

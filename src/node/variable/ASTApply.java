package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTApply implements ASTNode {
  private ASTNode ref;
  private ASTNode value;

  public ASTApply(ASTNode ref, ASTNode value) {
    this.ref = ref;
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue iref = ref.eval(interpreter);
    IValue ival = value.eval(interpreter);

    interpreter.apply(iref, ival);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.loadStaticLink();

    value.compile(compiler);

    // compiler.addFieldToFrame(id);
  }
}

package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import values.IValue;

public class ASTApply implements ASTNode {
  private ASTNode ref;
  private ASTNode value;

  public ASTApply(ASTNode ref, ASTNode value) {
    this.ref = ref;
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue iref = ref.eval(interpreter);
    IValue ival = value.eval(interpreter);

    interpreter.apply(iref, ival);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.loadStaticLink();

    ref.compile(compiler);

    String id = compiler.popFrameField();

    compiler.updateFrameField(id, value);
  }
}

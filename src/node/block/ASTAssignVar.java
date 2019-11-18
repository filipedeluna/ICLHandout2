package node.block;

import env.Interpreter;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTAssignVar extends ASTAssignment {
  private String id;
  private ASTNode node;

  public ASTAssignVar(String id, ASTNode node) {
    super(id, node);
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue val = node.eval(interpreter);

    interpreter.newVar(id, val);

    return null; // Nothing to return
  }
}

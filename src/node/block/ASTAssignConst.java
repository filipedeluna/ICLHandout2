package node.block;

import env.Interpreter;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;

public class ASTAssignConst extends ASTAssignment {
  private String id;
  private ASTNode node;

  public ASTAssignConst(String id, ASTNode node) {
    super(id, node);
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue val = node.eval(interpreter);

    interpreter.newConst(id, val);

    return null; // Nothing to return
  }
}

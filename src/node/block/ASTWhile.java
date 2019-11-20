package node.block;

import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import errors.interpreter.UnexpectedTypeException;
import node.ASTNode;
import value.IValue;
import value.VBool;

public final class ASTWhile implements ASTNode {
  private ASTNode condition;
  private ASTNode action;

  public ASTWhile(ASTNode condition, ASTNode action) {
    this.condition = condition;
    this.action = action;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    interpreter.beginEnvScope();

    while (verifyCondition(interpreter, condition)) {
      action.eval(interpreter);
    }

    interpreter.endEnvScope();

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    // TODO
  }

  private boolean verifyCondition(Interpreter interpreter, ASTNode condition) throws InterpreterException {
    IValue conditionResult = condition.eval(interpreter);

    if (!(conditionResult instanceof VBool))
      throw new UnexpectedTypeException(conditionResult.typeToString(), "bool");

    return ((VBool) conditionResult).get();
  }
}

package node.block;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
import interpreter.errors.InterpreterError;
import interpreter.errors.UnexpectedTypeError;
import node.ASTNode;
import values.IValue;
import values.VBool;

public final class ASTWhile implements ASTNode {
  private ASTNode condition;
  private ASTNode action;

  public ASTWhile(ASTNode condition, ASTNode action) {
    this.condition = condition;
    this.action = action;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    while (verifyCondition(interpreter, condition)) {
      action.eval(interpreter);
    }

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    int conditionStartLine = compiler.getCurrentLine();
    int actionLines = compiler.countLines(action);

    // While start
    condition.compile(compiler);
    int conditionEndLine = compiler.getCurrentLine();
    compiler.emit(ByteCode.IF, String.valueOf(conditionEndLine + actionLines + 2));

    // Action
    action.compile(compiler);
    compiler.emit(ByteCode.GOTO, String.valueOf(conditionStartLine));
  }

  /*
    UTILS
  */

  private boolean verifyCondition(Interpreter interpreter, ASTNode condition) throws InterpreterError {
    IValue conditionResult = condition.eval(interpreter);

    if (!(conditionResult instanceof VBool))
      throw new UnexpectedTypeError(conditionResult.type(), "bool");

    return ((VBool) conditionResult).get();
  }
}

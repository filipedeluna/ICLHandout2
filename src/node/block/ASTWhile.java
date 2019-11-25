package node.block;

import compiler.ByteCode;
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
    while (verifyCondition(interpreter, condition)) {
      action.eval(interpreter);
    }

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
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

  private boolean verifyCondition(Interpreter interpreter, ASTNode condition) throws InterpreterException {
    IValue conditionResult = condition.eval(interpreter);

    if (!(conditionResult instanceof VBool))
      throw new UnexpectedTypeException(conditionResult.typeToString(), "bool");

    return ((VBool) conditionResult).get();
  }
}

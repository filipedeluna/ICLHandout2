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

public final class ASTIf implements ASTNode {
  private ASTNode condition;
  private ASTNode action1;
  private ASTNode action2;

  public ASTIf(ASTNode condition, ASTNode action1, ASTNode action2) {
    this.condition = condition;
    this.action1 = action1;
    this.action2 = action2;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    boolean conditionResult = verifyCondition(interpreter, condition);

    IValue value;

    if (conditionResult)
      value = action1.eval(interpreter);
    else
      value = action2.eval(interpreter);

    return value;
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    condition.compile(compiler);

    int currentLine = compiler.getCurrentLine();
    int action1Lines = compiler.countLines(action1);

    // Start If
    compiler.emit(ByteCode.IF, String.valueOf(currentLine + action1Lines + 2));
    action1.compile(compiler);

    int action2Lines = compiler.countLines(action2);
    compiler.getCurrentLine();

    // Start then
    compiler.emit(ByteCode.GOTO, String.valueOf(currentLine + action2Lines + 2));
    action2.compile(compiler);
  }

  private boolean verifyCondition(Interpreter interpreter, ASTNode condition) throws InterpreterException {
    IValue conditionResult = condition.eval(interpreter);

    if (!(conditionResult instanceof VBool))
      throw new UnexpectedTypeException(conditionResult.typeToString(), "bool");

    return ((VBool) conditionResult).get();
  }
}

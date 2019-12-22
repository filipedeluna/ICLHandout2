package node.block;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TBool;
import types.TVoid;
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
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    while (verifyCondition(interpreter, condition)) {
      action.eval(interpreter);
    }

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    int conditionStartLine = compiler.getCurrentLine();
    int actionLines = compiler.countLines(action);

    // While start
    condition.compile(compiler);
    compiler.emit(ByteCode.IF, compiler.getCurrentLineOffset(actionLines + 2));

    // Action
    action.compile(compiler);
    compiler.emit(ByteCode.GOTO, String.valueOf(conditionStartLine));
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType condType = condition.typeCheck(typeChecker);

    action.typeCheck(typeChecker);

    if (!(condType instanceof TBool))
      throw new TypeCheckError("Invalid type for condition", "while", condType);

    return TVoid.SINGLETON;
  }

  /*
    UTILS
  */
  private boolean verifyCondition(Interpreter interpreter, ASTNode condition) throws InterpretationError {
    IValue conditionResult = condition.eval(interpreter);

    return ((VBool) conditionResult).get();
  }
}

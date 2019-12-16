package node.variable;

import compiler.ByteCode;
import compiler.Compiler;
import compiler.errors.InvalidTypeError;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
import interpreter.errors.InterpreterError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;
import values.VBool;
import values.VInt;

public class ASTInit implements ASTNode {
  private IValue value;

  public ASTInit(IValue value) {
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    return interpreter.init(value);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    String parsedVal;

    if (value instanceof VInt)
      parsedVal = value.toString();
    else if (value instanceof VBool)
      parsedVal = ((VBool) value).get() ? "1" : "0";
    else
      throw new InvalidTypeError(value, "int or bool");

    compiler.emit(ByteCode.PUSH, parsedVal);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    return null;
  }
}

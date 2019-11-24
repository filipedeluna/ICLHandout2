package node.variable;

import compiler.ByteCode;
import compiler.Compiler;
import errors.compiler.InvalidTypeException;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;
import value.VBool;
import value.VInt;

public class ASTInit implements ASTNode {
  private IValue value;

  public ASTInit(IValue value) {
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    return interpreter.init(value);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    String parsedVal;

    if (value instanceof VInt)
      parsedVal = value.toString();
    else if (value instanceof VBool)
      parsedVal = ((VBool) value).get() ? "1" : "0";
    else
      throw new InvalidTypeException(value, "int or bool");

    compiler.emit(ByteCode.PUSH, parsedVal);
  }
}

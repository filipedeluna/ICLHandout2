package node.logical;

import compiler.ByteCode;
import compiler.Compiler;
import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.IncompatibleTypeException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;
import value.VBool;

public class ASTComp implements ASTNode {
  private ASTNode node;

  public ASTComp(ASTNode node) {
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue value = node.eval(interpreter);

    if (value instanceof VBool) {
      boolean b = ((VBool) value).get();

      return new VBool(!b);
    }

    throw new IncompatibleTypeException("complement", value);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compiler.emit(ByteCode.PUSH, "-1");
    compiler.emit(ByteCode.XOR);
  }
}
package node.logical;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
import interpreter.errors.IncompatibleTypeError;
import interpreter.errors.InterpreterError;
import node.ASTNode;
import values.IValue;
import values.VBool;

public class ASTComp implements ASTNode {
  private ASTNode node;

  public ASTComp(ASTNode node) {
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    IValue value = node.eval(interpreter);

    if (value instanceof VBool) {
      boolean b = ((VBool) value).get();

      return new VBool(!b);
    }

    throw new IncompatibleTypeError("complement", value);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    node.compile(compiler);

    compiler.emit(ByteCode.CONST_0);
    compiler.compare(ByteCode.EQUALS);
  }
}

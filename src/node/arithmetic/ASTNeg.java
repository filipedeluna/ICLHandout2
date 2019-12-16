package node.arithmetic;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompilerError;
import interpreter.errors.IncompatibleTypeError;
import interpreter.errors.InterpreterError;
import node.ASTNode;
import values.IValue;
import values.VInt;

public class ASTNeg implements ASTNode {
  private ASTNode node;

  public ASTNeg(ASTNode node) {
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterError {
    IValue value = node.eval(interpreter);

    if (value instanceof VInt) {
      int i = ((VInt) value).get();

      return new VInt(-i);
    }

    throw new IncompatibleTypeError("negate", value);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerError {
    node.compile(compiler);

    compiler.emit(ByteCode.NEG);
  }
}

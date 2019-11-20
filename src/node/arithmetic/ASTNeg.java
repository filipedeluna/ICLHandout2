package node.arithmetic;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.IncompatibleTypeException;
import errors.interpreter.InterpreterException;
import node.ASTNode;
import value.IValue;
import value.VInt;

public class ASTNeg implements ASTNode {
  private ASTNode node;

  public ASTNeg(ASTNode node) {
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpreterException {
    IValue value = node.eval(interpreter);

    if (value instanceof VInt) {
      int i = ((VInt) value).get();

      return new VInt(-i);
    }

    throw new IncompatibleTypeException("negate", value);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    node.compile(compiler);

    compiler.emit(ByteCode.NEG);
  }
}

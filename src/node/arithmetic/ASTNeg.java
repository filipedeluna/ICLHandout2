package node.arithmetic;

import compiler.ByteCode;
import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TInt;
import values.IValue;
import values.VInt;

public class ASTNeg implements ASTNode {
  private ASTNode node;

  public ASTNeg(ASTNode node) {
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue value = node.eval(interpreter);

    int i = ((VInt) value).get();

    return new VInt(-i);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    node.compile(compiler);

    compiler.emit(ByteCode.NEG);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type = node.typeCheck(typeChecker);

    if (type instanceof TInt)
      return TInt.SINGLETON;

    throw new TypeCheckError("Invalid types", "negate", type);
  }
}

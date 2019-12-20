package node.logical;

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
import values.IValue;
import values.VBool;

public class ASTComp implements ASTNode {
  private ASTNode node;

  public ASTComp(ASTNode node) {
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue value = node.eval(interpreter);

    boolean b = ((VBool) value).get();

    return new VBool(!b);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    node.compile(compiler);

    compiler.emit(ByteCode.CONST_0);
    compiler.compare(ByteCode.EQUALS);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type = node.typeCheck(typeChecker);

    if (!(type instanceof TBool))
      throw new TypeCheckError("Invalid type for operation", "complement", type);

    return TBool.SINGLETON;
  }
}

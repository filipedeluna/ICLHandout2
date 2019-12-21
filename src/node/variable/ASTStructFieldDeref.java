package node.variable;

import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TStruct;
import values.IValue;
import values.VStruct;

public final class ASTStructFieldDeref implements ASTNode {
  private String structId;
  private String fieldId;

  public ASTStructFieldDeref(String structId, String fieldId) {
    this.structId = structId;
    this.fieldId = fieldId;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue struct = interpreter.deref(structId);

    return ((VStruct) struct).get(fieldId);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.getFrameStructField(structId, fieldId);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType struct = typeChecker.find(structId);

    if (!(struct instanceof TStruct))
      throw new TypeCheckError("Variable is not of type struct", "struct deref");

    IType field = ((TStruct) struct).get(fieldId);

    if (field == null)
      throw new TypeCheckError("Field does not exist in struct", "struct deref");

    return field;
  }
}

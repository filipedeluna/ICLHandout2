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
import types.TStructCell;
import values.IValue;
import values.VCell;
import values.VStructCell;

public final class ASTStructFieldVar implements ASTNode {
  private String structId;
  private String fieldId;

  public ASTStructFieldVar(String structId, String fieldId) {
    this.structId = structId;
    this.fieldId = fieldId;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    VCell structCell = interpreter.find(structId);

    return new VStructCell(structCell.get(), fieldId);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
   // compiler.pushFrameField(id);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType struct = typeChecker.find(structId);

    if (!(struct instanceof TStruct))
      throw new TypeCheckError("Variable is not a struct", "Struct field select");

    return new TStructCell(structId, fieldId);
  }
}

package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.*;
import values.IValue;

public class ASTApply implements ASTNode {
  private ASTNode ref;
  private ASTNode value;

  public ASTApply(ASTNode ref, ASTNode value) {
    this.ref = ref;
    this.value = value;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue iref = ref.eval(interpreter);
    IValue ival = value.eval(interpreter);

    interpreter.apply(iref, ival);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.loadStaticLink();

    ref.compile(compiler);

    String id = compiler.popFrameField();

    compiler.updateFrameField(id, value);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type1 = ref.typeCheck(typeChecker);
    IType type2 = value.typeCheck(typeChecker);

    if (type2 instanceof TVoid)
      throw new TypeCheckError("Value expected", "apply");

    if (type2 instanceof TFun || type2 instanceof TCell)
      throw new TypeCheckError("Invalid applied type", "apply", type2);

    if (!(type1 instanceof TCell))
      throw new TypeCheckError("Variable not found in right side", "apply");

    String type1Id = ((TCell) type1).getId();

    if (type1Id.length() == 0)
      throw new TypeCheckError("Corrupted variable", "apply");

    if (type1 instanceof TStructCell) {
      String fieldId = ((TStructCell) type1).getFieldId();

      IType structType = typeChecker.find(type1Id);

      if (!(structType instanceof TStruct))
        throw new TypeCheckError("Variable is not a struct", "apply");

      if (!((TStruct) structType).contains(fieldId))
        throw new TypeCheckError("Field " + fieldId + " does not exist in struct", "apply");

      IType fieldType = ((TStruct) structType).get(fieldId);

      if (!fieldType.equals(type2))
        throw new TypeCheckError("Invalid value applied, types do not match", "apply", fieldType, type2);
    }

    IType varType = typeChecker.find(type1Id);

    if (!varType.equals(type2))
      throw new TypeCheckError("Invalid value applied, types do not match", "apply", varType, type2);

    if (varType instanceof TStruct) {
      typeChecker.overwrite(type1Id, type2);
    }

    return TVoid.SINGLETON;
  }
}

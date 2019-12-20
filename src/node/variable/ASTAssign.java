package node.variable;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TCell;
import types.TVoid;
import values.*;

public class ASTAssign implements ASTNode {
  private String id;
  private ASTNode value;
  private IType type;

  public ASTAssign(String id, IType type, ASTNode value) {
    this.id = id;
    this.value = value;
    this.type = type;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue iv = value.eval(interpreter);

    interpreter.assign(id, iv);

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.loadStaticLink();

    value.compile(compiler);

    IValue value = compiler.peekTempValue();

    if (value instanceof VBool || value instanceof VInt)
      compiler.addFrameField(id, "I");

    if (value instanceof VString)
      compiler.addFrameField(id, "Ljava/lang/String;");

    //if (value instanceof VStruct)
    // compiler.
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType cellType = value.typeCheck(typeChecker);

    if (!(cellType instanceof TCell))
      throw new TypeCheckError("Expected a reference to a value", "assign");

    typeChecker.assignTempType(id, type);

    return TVoid.SINGLETON;
  }
}

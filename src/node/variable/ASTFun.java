package node.variable;

import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import interpreter.Interpreter;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TBool;
import values.IValue;
import values.VBool;

import java.util.ArrayList;

public final class ASTFun implements ASTNode {
  private ArrayList<IType> paramTypes;
  private String node;

  public ASTFun(ArrayList<IType> paramTypes, ASTNode node) {
    this.node = node;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    if (!(val instanceof VBool))
      throw new InterpretationError("Unexpected value type", "cast to bool", val.type());

    return val;

    return interpreter.find(id);
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    // TODO add function to frame and write it to the file
    compiler.pushFrameField(id);
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    if (!(val.type() instanceof TBool))
      throw new TypeCheckError("Unexpected type", "cast to bool", val.type());

    return TBool.SINGLETON;
  }
}

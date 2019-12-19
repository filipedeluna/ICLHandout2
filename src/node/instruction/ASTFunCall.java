package node.instruction;

import compiler.ByteCode;
import compiler.Compiler;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import types.TFun;
import values.IValue;
import values.VCell;
import values.VFun;
import values.VInt;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public final class ASTFunCall implements ASTNode {
  private String functionId;
  private ArrayList<ASTNode> funcParams;

  public ASTFunCall(String functionId, ArrayList<ASTNode> funcParams) {
    this.functionId = functionId;
    this.funcParams = funcParams;
  }

  public ASTFunCall(String functionId) {
    this.functionId = functionId;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue value = interpreter.deref(functionId);

    if (!(value instanceof VFun))
      throw new InterpretationError("A reference to a function was expected from id: " + functionId, "function call");

    interpreter.beginEnvScope();

    ArrayList<String> paramNames = new ArrayList<>(((VFun) value).getParams().keySet());

    IValue paramValue;
    String paramName;
    for (int i = 0; i < paramNames.size(); i++) {
      paramValue = funcParams.get(i).eval(interpreter);
      paramName = paramNames.get(i);

      interpreter.init(paramValue);
      interpreter.assign(paramName, paramValue);
    }

    IValue result = ((VFun) value).getBlock().eval(interpreter);

    interpreter.endEnvScope();

    return result;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    // compiler.emit(ByteCode.PUSH, String.valueOf(val));
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type = typeChecker.find(functionId);

    if (!(type instanceof TFun))
      throw new TypeCheckError("A reference to a function was expected from id: " + functionId, "function call");

    ArrayList<IType> paramTypes = ((TFun) type).getParameterTypes();

    if (paramTypes.size() != funcParams.size())
      throw new TypeCheckError("Wrong number of parameters for function with id: " + functionId, "function call");

    IType funcParamType;
    for (int i = 0; i < paramTypes.size(); i++) {
      funcParamType = funcParams.get(i).typeCheck(typeChecker);

      if (!paramTypes.get(i).equals(funcParamType))
        throw new TypeCheckError("Wrong parameter types for function with id: " + functionId, "function call");
    }

    return ((TFun) type).getReturnType();
  }
}

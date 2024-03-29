package node.arithmetic;

import compiler.ByteCode;
import compiler.Compiler;
import compiler.CompilerType;
import compiler.cache.CacheEntry;
import compiler.errors.CompileError;
import interpreter.Interpreter;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.*;
import values.IValue;
import values.VInt;
import values.VString;
import values.VStruct;

import java.util.Map.Entry;

public class ASTArithmetic implements ASTNode {
  private ArithmeticOperation operation;
  private ASTNode left;
  private ASTNode right;

  public ASTArithmetic(ArithmeticOperation operation, ASTNode left, ASTNode right) {
    this.operation = operation;
    this.left = left;
    this.right = right;
  }

  @Override
  public IValue eval(Interpreter interpreter) throws InterpretationError {
    IValue v1 = left.eval(interpreter);
    IValue v2 = right.eval(interpreter);

    if (v1 instanceof VInt) {
      int i1 = ((VInt) v1).get();
      int i2 = ((VInt) v2).get();

      switch (operation) {
        case ADD:
          return new VInt(i1 + i2);
        case SUB:
          return new VInt(i1 - i2);
        case MUL:
          return new VInt(i1 * i2);
        case DIV:
          if (i2 == 0)
            throw new InterpretationError("Attempted to divide by 0", operation.name());
          return new VInt(i1 / i2);
      }
    }

    if (v1 instanceof VString)
      return new VString(v1.asString() + v2.asString(), false);

    if (operation == ArithmeticOperation.ADD) {
      if (v1 instanceof VStruct) {
        ((VStruct) v1).merge((VStruct) v2);

        return v1;
      }
    }

    return null;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    left.compile(compiler);
    right.compile(compiler);

    CacheEntry cacheEntry = compiler.cache.pop();
    CacheEntry cacheEntry2 = compiler.cache.pop();
    CompilerType type = cacheEntry.getType();

    switch (operation) {
      case ADD:
        if (type == CompilerType.INT)
          compiler.emit(ByteCode.ADD);

        if (type == CompilerType.STRING)
          compiler.stringConcat();

        if (type == CompilerType.STRUCT) {
          for (Entry<String, CompilerType> entry : cacheEntry.getStructParams().entrySet())
            compiler.addFieldToFrameStructField(cacheEntry2.getField(), entry.getKey(), entry.getValue());
        }

        break;
      case SUB:
        compiler.emit(ByteCode.SUB);
        break;
      case MUL:
        compiler.emit(ByteCode.MUL);
        break;
      case DIV:
        compiler.emit(ByteCode.DIV);
        break;
    }
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    IType type1 = left.typeCheck(typeChecker);
    IType type2 = right.typeCheck(typeChecker);

    if (!type1.equals(type2))
      throw new TypeCheckError("Type mismatch", operation.name(), type1, type2);

    if (type1 instanceof TInt)
      return TInt.SINGLETON;

    if (type1 instanceof TBool)
      return TBool.SINGLETON;

    if (type1 instanceof TStruct) {
      if (operation == ArithmeticOperation.ADD) {
        if (!((TStruct) type1).merge((TStruct) type2))
          throw new TypeCheckError("Duplicate parameter in struct", operation.name());

        return type1;
      }
    }

    if (type1 instanceof TString)
      return TString.SINGLETON;

    throw new TypeCheckError("Invalid types", operation.name(), type1);
  }
}

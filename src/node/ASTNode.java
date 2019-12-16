package node;

import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;

import compiler.Compiler;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;

public interface ASTNode {
  IValue eval(Interpreter interpreter) throws InterpretationError;

  void compile(Compiler compiler) throws CompileError;

  IType typeCheck(TypeChecker typeChecker) throws TypeCheckError;
}

package node;

import interpreter.Interpreter;
import compiler.errors.CompilerError;
import interpreter.errors.InterpreterError;

import compiler.Compiler;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;

public interface ASTNode {
  IValue eval(Interpreter interpreter) throws InterpreterError;

  void compile(Compiler compiler) throws CompilerError;

  IType typeCheck(TypeChecker typeChecker) throws TypeCheckError;
}

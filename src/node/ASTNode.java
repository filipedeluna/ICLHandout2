package node;

import env.Interpreter;
import errors.compiler.CompilerException;
import errors.interpreter.InterpreterException;

import compiler.Compiler;
import value.IValue;

public interface ASTNode {
  IValue eval(Interpreter interpreter) throws InterpreterException;

  void compile(Compiler compiler) throws CompilerException;
}

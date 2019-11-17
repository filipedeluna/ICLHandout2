package node;

import env.Environment;
import errors.compiler.CompilerException;
import errors.eval.EvaluationException;
import errors.env.EnvironmentException;

import compiler.Compiler;
import value.IValue;

public interface ASTNode {
  IValue eval(Environment env) throws EvaluationException, EnvironmentException;

  void compile(Compiler compiler) throws CompilerException;
}

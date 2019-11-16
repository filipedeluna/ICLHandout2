package node;

import env.Environment;
import errors.compiler.CompilerException;
import errors.eval.EvaluationException;
import errors.env.EnvironmentException;

import compiler.Compiler;

public interface ASTNode {
  int eval(Environment env) throws EvaluationException, EnvironmentException;

  void compile(Compiler compiler) throws CompilerException;
}

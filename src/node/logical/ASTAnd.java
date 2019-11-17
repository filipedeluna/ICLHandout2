package node.logical;

import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;
import value.IValue;

public class ASTAnd extends ASTLogical {
  public ASTAnd(ASTNode left, ASTNode right) {
    super(left, right);
  }

  @Override
  public IValue eval(Environment env) throws EvaluationException, EnvironmentException {
    return eval(LogicalOperation.AND, env);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compile(LogicalOperation.AND, compiler);
  }
}

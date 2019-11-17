package node.logical;

import compiler.Compiler;
import env.Environment;
import errors.compiler.CompilerException;
import errors.env.EnvironmentException;
import errors.eval.EvaluationException;
import node.ASTNode;
import value.IValue;

public class ASTComp extends ASTLogical {
  public ASTComp(ASTNode left) {
    super(left);
  }

  @Override
  public IValue eval(Environment env) throws EvaluationException, EnvironmentException {
    return eval(LogicalOperation.COMP, env);
  }

  @Override
  public void compile(Compiler compiler) throws CompilerException {
    compile(LogicalOperation.COMP, compiler);
  }
}

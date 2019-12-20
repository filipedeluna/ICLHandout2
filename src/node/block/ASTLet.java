package node.block;

import compiler.Compiler;
import interpreter.Interpreter;
import compiler.errors.CompileError;
import interpreter.errors.InterpretationError;
import node.ASTNode;
import node.variable.ASTAssign;
import typechecker.TypeChecker;
import typechecker.errors.TypeCheckError;
import types.IType;
import values.IValue;

import java.util.ArrayList;

public class ASTLet implements ASTNode {
  private ArrayList<ASTAssign> assignments;
  private ASTNode node;

  public ASTLet(ASTNode node, ArrayList<ASTAssign> assignments) {
    this.node = node;
    this.assignments = assignments;
  }

  public IValue eval(Interpreter interpreter) throws InterpretationError {
    interpreter.beginEnvScope();

    for (ASTAssign assignment : assignments) {
      assignment.eval(interpreter);
    }

    IValue val = node.eval(interpreter);

    interpreter.endEnvScope();

    return val;
  }

  @Override
  public void compile(Compiler compiler) throws CompileError {
    compiler.beginFrame();

    for (ASTAssign assignment : assignments)
      assignment.compile(compiler);

    node.compile(compiler);

    compiler.endFrame();
  }

  @Override
  public IType typeCheck(TypeChecker typeChecker) throws TypeCheckError {
    typeChecker.beginEnvScope();

    for (ASTAssign assignment : assignments) {
      assignment.typeCheck(typeChecker);
    }

    IType returnType = node.typeCheck(typeChecker);

    typeChecker.endEnvScope();

    return returnType;
  }
}

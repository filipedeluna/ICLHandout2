package compiler;

import compiler.errors.CompileError;
import node.ASTNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Cache {
  private String field;
  private String subField;
  private CompilerType type;
  private LinkedHashMap<String, CompilerType> structParams;

  private ArrayList<CompilerType> funParams;
  private ASTNode funBlock;

  Cache() {
  }

  public String getField() throws CompileError {
    checkError(field == null, "get field");

    String temp = field;
    field = null;

    return temp;
  }

  public void setField(String field) throws CompileError {
    checkError(this.field != null, "set field");

    this.field = field;
  }

  // --------------------------------------------

  public String getSubField() throws CompileError {
    checkError(subField == null, "get subfield");

    String temp = subField;
    subField = null;

    return temp;
  }

  public void setSubField(String subField) throws CompileError {
    checkError(this.subField != null, "set subfield");

    this.subField = subField;
  }

  // --------------------------------------------

  public CompilerType getType() throws CompileError {
    checkError(type == null, "get type");

    CompilerType temp = type;
    type = null;

    return temp;
  }

  public void setType(CompilerType type) throws CompileError {
    checkError(this.type != null, "set type");

    if (type == CompilerType.STRUCT)
      throw new CompileError("Invalid struct parameter", "set type");

    this.type = type;
  }

  // --------------------------------------------

  public LinkedHashMap<String, CompilerType> getStructParams() throws CompileError {
    checkError(structParams == null, "get struct params");

    LinkedHashMap<String, CompilerType> temp = structParams;
    structParams = null;

    return temp;
  }

  public void setTypeStruct(LinkedHashMap<String, CompilerType> structParams) throws CompileError {
    checkError(this.structParams != null || type != null, "set struct params");

    this.type = CompilerType.STRUCT;
    this.structParams = structParams;
  }

  // -----------------------------------------

  public ArrayList<CompilerType> getFunParams() throws CompileError {
    checkError(structParams == null, "get fun params");

    ArrayList<CompilerType> temp = funParams;
    funParams = null;

    return temp;
  }

  public ASTNode getFunBlock() throws CompileError {
    checkError(funBlock == null, "get fun block");

    ASTNode temp = funBlock;
    funBlock = null;

    return temp;
  }

  public void setTypeFun(ArrayList<CompilerType> funParams, ASTNode funBlock) throws CompileError {
    checkError(this.funParams != null || type != null, "set function params");

    this.type = CompilerType.FUN;
    this.funBlock = funBlock;
    this.funParams = funParams;
  }

  /*
    UTILS
  */

  private void checkError(boolean cond, String op) throws CompileError {
    if (cond)
      throw new CompileError("Cache register is empty", "cache - " + op);
  }
}

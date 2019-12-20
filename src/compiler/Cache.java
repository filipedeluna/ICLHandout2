package compiler;

import compiler.errors.CompileError;

import java.util.LinkedHashMap;

public class Cache {
  private String field;
  private String subField;
  private CompilerType type;
  private LinkedHashMap<String, CompilerType> structParams;

  Cache() {
  }

  public String getField() throws CompileError {
    if (field == null)
      throw new CompileError("Register is empty", "get cache field");

    String temp = field;
    field = null;

    return temp;
  }

  public void setField(String field) throws CompileError {
    if (this.field != null)
      throw new CompileError("Register is not empty", "set cache field");

    this.field = field;
  }

  // --------------------------------------------

  public String getSubField() throws CompileError {
    if (subField == null)
      throw new CompileError("Register is empty", "get cache subfield");

    String temp = subField;
    subField = null;

    return temp;
  }

  public void setSubField(String subField) throws CompileError {
    if (this.subField != null)
      throw new CompileError("Register is not empty", "set cache subfield");

    this.subField = subField;
  }

  // --------------------------------------------

  public CompilerType getType() throws CompileError {
    if (type == null)
      throw new CompileError("Register is empty", "get cache type");

    CompilerType temp = type;
    type = null;

    return temp;
  }

  public void setType(CompilerType type) throws CompileError {
    if (this.type != null)
      throw new CompileError("Register is not empty", "set cache type");

    if (type == CompilerType.STRUCT)
      throw new CompileError("Invalid struct parameter", "set cache type");

    this.type = type;
  }

  // --------------------------------------------

  public LinkedHashMap<String, CompilerType> getStructParams() throws CompileError {
    if (structParams == null)
      throw new CompileError("Register is empty", "get cache struct params");

    LinkedHashMap<String, CompilerType> temp = structParams;
    structParams = null;

    return temp;
  }

  public void setTypeStruct(LinkedHashMap<String, CompilerType> structParams) throws CompileError {
    if (this.structParams != null || type != null)
      throw new CompileError("Register is not empty", "set cache struct params");

    this.type = CompilerType.STRUCT;
    this.structParams = structParams;
  }
}

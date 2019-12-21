package compiler.cache;

import compiler.CompilerType;
import compiler.errors.CompileError;
import node.ASTNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CacheEntry {
  private String field;
  private String subField;
  private CompilerType type;
  private LinkedHashMap<String, CompilerType> structParams;

  private LinkedHashMap<String, CompilerType> funParams;
  private ASTNode funBlock;

  public CacheEntry(CompilerType type, String field, String subField) {
    this.type = type;
    this.field = field;
    this.subField = subField;
  }

  public CacheEntry(LinkedHashMap<String, CompilerType> funParams, ASTNode funBlock) {
    type = CompilerType.FUN;
    this.funBlock = funBlock;
    this.funParams = funParams;
  }

  public CacheEntry(LinkedHashMap<String, CompilerType> structParams) {
    type = CompilerType.STRUCT;
    this.structParams = structParams;
  }

  public CacheEntry(CompilerType type, String field) {
    this.type = type;
    this.field = field;
  }

  public CacheEntry(CompilerType type) {
    this.type = type;
  }

  public CacheEntry(String field) {
    this.field = field;
  }

  public String getField() throws CompileError {
    if (field == null)
      throw new CompileError("Field is not set", "get field from cache");

    return field;
  }

  public String getSubField() throws CompileError {
    if (subField == null)
      throw new CompileError("Field is not set", "get subField from cache");

    return subField;
  }

  public CompilerType getType() throws CompileError {
    if (type == null)
      throw new CompileError("Field is not set", "get type from cache");

    return type;
  }

  public LinkedHashMap<String, CompilerType> getStructParams() throws CompileError {
    if (structParams == null)
      throw new CompileError("Field is not set", "get structParams from cache");

    return structParams;
  }

  public LinkedHashMap<String, CompilerType> getFunParams() throws CompileError {
    if (funParams == null)
      throw new CompileError("Field is not set", "get funParams from cache");

    return funParams;
  }

  public ASTNode getFunBlock() throws CompileError {
    if (funBlock == null)
      throw new CompileError("Field is not set", "get funBlock from cache");

    return funBlock;
  }
}

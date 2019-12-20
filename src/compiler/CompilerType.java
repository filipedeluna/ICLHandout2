package compiler;

public enum CompilerType {
  INT,
  BOOL,
  STRING,
  STRUCT,
  FUN,
  VOID,
  CELL,
  STRUCT_CELL;


  public boolean isLit() {
    return this == INT || this == BOOL || this == STRING;
  }
}


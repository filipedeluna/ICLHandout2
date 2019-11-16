package compiler;

public enum ByteCode {
  ADD("iadd"),
  MUL("imul"),
  SUB("isub"),
  DIV("idiv"),

  AND("iand"),
  OR("ior"),

  PUSH("sipush"),
  DUP("dup"),
  STORE("astore"),
  LOAD("aload"),

  INVOKE_SPECIAL("invokespecial"),
  PUT_FIELD("putfield"),
  GET_FIELD("getfield"),

  CONST_NULL("aconst_null"),
  NEW("new");

  private String value;

  ByteCode(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}

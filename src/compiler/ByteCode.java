package compiler;

public enum ByteCode {
  ADD("iadd"),
  MUL("imul"),
  SUB("isub"),
  DIV("idiv"),
  NEG("ineg"),

  AND("iand"),
  OR("ior"),
  XOR("ixor"),

  PUSH("sipush"),
  LOAD_C("ldc"),
  DUP("dup"),
  STORE("astore"),
  LOAD("aload"),

  INVOKE_SPECIAL("invokespecial"),
  INVOKE_STATIC("invokestatic"),
  INVOKE_VIRTUAL("invokevirtual"),
  INVOKE_NON_VIRTUAL("invokenonvirtual"),
  PUT_FIELD("putfield"),
  GET_FIELD("getfield"),
  GET_STATIC("getstatic"),

  CONST_NULL("aconst_null"),
  NEW("new"),

  EQUALS("if_icmpeq"),
  NOT_EQUALS("if_icmpne"),
  GREATER_OR_EQ("if_icmpge"),
  GREATER("if_icmpgt"),
  LESSER_OR_EQ("if_icmple"),
  LESSER("if_icmplt"),

  IF("ifeq"),

  GOTO("goto"),
  RETURN("return"),

  CONST_0("iconst_0"),
  CONST_1("iconst_1"),

  STORE_INT("istore_1"),
  LOAD_INT("istore_2");

  private String value;

  ByteCode(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}

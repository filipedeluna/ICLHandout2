package compiler;

import compiler.errors.CompileError;

import java.util.ArrayList;

final class FrameField {
  private String fieldId;
  private boolean isStruct;
  private ArrayList<String> frameList;
  private ArrayList<String> structFields;

  FrameField(String fieldId, boolean isStruct) {
    this.fieldId = fieldId;
    this.isStruct = isStruct;

    structFields = new ArrayList<>();
    frameList = new ArrayList<>();
  }

  public void addStructField(String fieldId) {
    if (frameList.contains(fieldId))
     // throw new CompileError("Struct already contains this value", )
    frameList.add(fieldId);
  }


  ArrayList<String> getFrameList() {
    return frameList;
  }

  void setFrameList(ArrayList<String> frameList) {
    this.frameList = frameList;
  }

  String getFieldId() {
    return fieldId;
  }

  public boolean isStruct() {
    return isStruct;
  }
}

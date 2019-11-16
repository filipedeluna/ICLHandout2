package compiler;

import java.util.ArrayList;

class CompilerFrameField {
  private ArrayList<String> frameList;
  private String fieldId;

  CompilerFrameField(String fieldId, ArrayList<String> frameList) {
    this.fieldId = fieldId;
    this.frameList = frameList;
  }

  ArrayList<String> getFrameList() {
    return frameList;
  }

  String getFieldId() {
    return fieldId;
  }
}

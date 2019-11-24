package compiler;

import java.util.ArrayList;

final class FrameField {
  private ArrayList<String> frameList;
  private String fieldId;

  FrameField(String fieldId, ArrayList<String> frameList) {
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

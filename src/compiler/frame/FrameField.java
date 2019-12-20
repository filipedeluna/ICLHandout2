package compiler.frame;
import compiler.CompilerType;

import java.util.ArrayList;

public class FrameField {
  private String fieldId;
  private ArrayList<String> frameList;
  private CompilerType type;

  public FrameField(String fieldId, CompilerType type) {
    this.fieldId = fieldId;
    this.type = type;

    frameList = new ArrayList<>();
  }

  public CompilerType getType() {
    return type;
  }

  public ArrayList<String> getFrameList() {
    return frameList;
  }

  public void setFrameList(ArrayList<String> frameList) {
    this.frameList = frameList;
  }

  public String getFieldId() {
    return fieldId;
  }
}

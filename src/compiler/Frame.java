package compiler;

import errors.compiler.CompilerException;
import errors.compiler.CompilerDuplicateVariableException;
import errors.compiler.CompilerUndefinedVariableException;

import java.util.ArrayList;

final class Frame {
  private String frameId;
  private Frame parentFrame;
  private ArrayList<String> fields;

  Frame(String frameId) {
    this.frameId = frameId;
    fields = new ArrayList<>();
  }

  Frame(String frameId, Frame parentFrame) {
    this.frameId = frameId;
    this.parentFrame = parentFrame;
    fields = new ArrayList<>();
  }

  String addField(String id) {
    fields.add(id);

    return "x" + (fields.size() - 1);
  }

  // Obtain a variable's respective frame field id and the list of sub-frames to get to it
  FrameField getFrameField(String varId) throws CompilerException {
    ArrayList<String> subFrameList = new ArrayList<>();

    Frame cFrame = this;

    while (cFrame != null) {
      subFrameList.add(cFrame.frameId);

      if (cFrame.containsVar(varId))
        return new FrameField(cFrame.getField(varId), subFrameList);

      cFrame = cFrame.parentFrame;
    }

    return null;
  }

  boolean hasField(String id) {
    return fields.contains(id);
  }

  String getFrameId() {
    return frameId;
  }

  Frame getParentFrame() {
    return parentFrame;
  }

  /*
    UTILS
  */
  private boolean containsVar(String varId) {
    return fields.contains(varId);
  }

  private String getField(String varId) throws CompilerException {
    for (int i = 0; i < fields.size(); i++) {
      if (fields.get(i).equals(varId))
        return "x" + i;
    }

    throw new CompilerUndefinedVariableException(varId);
  }
}
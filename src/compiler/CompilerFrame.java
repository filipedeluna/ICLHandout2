package compiler;

import errors.compiler.CompilerException;
import errors.compiler.CompilerDuplicateVariableException;
import errors.compiler.CompilerUndefinedVariableException;

import java.util.ArrayList;

final class CompilerFrame {
  private String frameId;
  private CompilerFrame parentFrame;
  private ArrayList<String> fields;

  CompilerFrame(String frameId) {
    this.frameId = frameId;
    fields = new ArrayList<>();
  }

  CompilerFrame(String frameId, CompilerFrame parentFrame) {
    this.frameId = frameId;
    this.parentFrame = parentFrame;
    fields = new ArrayList<>();
  }

  String addField(String id) throws CompilerException {
    if (fields.contains(id))
      throw new CompilerDuplicateVariableException(id);

    fields.add(id);

    return "x" + (fields.size() - 1);
  }

  // Obtain a variable's respective frame field id and the list of sub-frames to get to it
  CompilerFrameField getFrameField(String varId) throws CompilerException {
    ArrayList<String> subFrameList = new ArrayList<>();

    CompilerFrame cFrame = this;

    while (cFrame != null) {
      subFrameList.add(cFrame.frameId);

      if (cFrame.containsVar(varId))
        return new CompilerFrameField(cFrame.getField(varId), subFrameList);

      cFrame = cFrame.parentFrame;
    }

    throw new CompilerUndefinedVariableException(varId);
  }

  String getFrameId() {
    return frameId;
  }

  CompilerFrame getParentFrame() {
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

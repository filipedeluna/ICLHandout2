package compiler;

import compiler.errors.CompileError;

import java.io.*;
import java.util.HashSet;

class BufferedCompilerWriter extends BufferedWriter {


  private String outputFolder;
  private String outputFile;

  private HashSet<String> fileSet;

  private int currentLine;
  private int lineCounter;
  private boolean counterFlag;

  BufferedCompilerWriter(String outputFile, String outputFolder) throws IOException {
    super(new FileWriter(new File(outputFile)));

    this.outputFile = outputFile;
    this.outputFolder = outputFolder;
    fileSet = new HashSet<>();

    fileSet.add(outputFile + outputFolder);

    counterFlag = false;
    currentLine = 0;
    lineCounter = 0;
  }

  void writeBytecode(ByteCode byteCode, String params) throws CompileError {
    writeLine(byteCode.toString() + " " + params, true);
    flush2();
  }

  void writeBytecode(ByteCode byteCode) throws CompileError {
    writeBytecode(byteCode, "");
  }

  void writeLine(String string) throws CompileError {
    writeLine(string, false);
  }

  void writeNewLine() throws CompileError {
    writeLine("");
  }

  int getCurrentLine() {
    return currentLine;
  }

  String getCurrentLineOffset(int offset) {
    return formatLine(currentLine + offset);
  }

  void startLineCounter() {
    lineCounter = 0;
    counterFlag = true;
  }

  int endLineCounter() {
    int tempVal = lineCounter;

    lineCounter = 0;
    counterFlag = false;

    return tempVal;
  }

  /*
     UTILS
  */

  private void writeLine(String string, boolean tab) throws CompileError {
    if (!counterFlag) {
      write2(formatLine(currentLine) + ": " + (tab ? '\t' : "") + string);
      newLine2();
      currentLine++;
    } else
      lineCounter++;
  }

  void deleteFiles() throws CompileError {
    File file;

    // Close writer
    try {
      close();

      for (String fileName : fileSet) {
        file = new File(fileName);

        // Try to delete file
        if (!file.delete())
          throw new CompileError("Failed to delete leftover files", "delete files");
      }
    } catch (IOException e) {
      throw new CompileError("Failed to delete leftover files", "delete files");
    }
  }

  BufferedWriter getWriter(String frameId) throws CompileError {
    try {
      String fileName = outputFolder + frameId + ".j";
      File outputFile = new File(fileName);

      fileSet.add(fileName);

      return new BufferedWriter(new FileWriter(outputFile));
    } catch (IOException e) {
      throw new CompileError("Failed to write to file" + frameId + ".j", "write to frame file");
    }
  }

  /*
     OVERRIDES
  */

  private void write2(String s) throws CompileError {
    try {
      super.write(s);
    } catch (IOException e) {
      throw new CompileError("Failed to write to file" + outputFile, "write to file");
    }
  }

  private void newLine2() throws CompileError {
    try {
      super.newLine();
    } catch (IOException e) {
      throw new CompileError("Failed to write to file" + outputFile, "write to file");
    }
  }

  void flush2() throws CompileError {
    try {
      super.flush();
    } catch (IOException e) {
      throw new CompileError("Failed to write to file" + outputFile, "write to file");
    }
  }

  void close2() throws CompileError {
    try {
      super.close();
    } catch (IOException e) {
      throw new CompileError("Failed to write to file" + outputFile, "write to file");
    }
  }

  String formatLine(int line) {
    if (line < 10)
      return "00" + line;

    if (line < 100)
      return "0" + line;

    return String.valueOf(line);
  }

  /*
    UTILS
  */

}

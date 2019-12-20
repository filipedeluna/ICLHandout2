package compiler.writer;

import compiler.ByteCode;
import compiler.errors.CompileError;

import java.io.*;

final class BufferedCompilerWriter extends BufferedWriter {
  private String outputFileName;

  private int currentLine;
  private int lineCounter;
  private boolean counterFlag;

  BufferedCompilerWriter(File outputFile) throws IOException {
    super(new FileWriter(outputFile, true));

    this.outputFileName = outputFile.getName();

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

  void writeLine(String string, boolean tab) throws CompileError {
    if (!counterFlag) {
      write2(addLineNumber(currentLine) + ": " + (tab ? '\t' : "") + string);
      newLine2();
      currentLine++;
    } else
      lineCounter++;
  }

  void writeLine(String string) throws CompileError {
    writeLine(string, false);
  }

  public void writeNewLine() throws CompileError {
    writeLine("", false);
  }

  int getCurrentLine() {
    return currentLine;
  }

  String getCurrentLineOffset(int offset) {
    return addLineNumber(currentLine + offset);
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
     OVERRIDES
  */

  private void write2(String s) throws CompileError {
    try {
      super.write(s);
    } catch (IOException e) {
      throw new CompileError("Failed to write to file" + outputFileName, "write to file");
    }
  }

  private void newLine2() throws CompileError {
    try {
      super.newLine();
    } catch (IOException e) {
      throw new CompileError("Failed to write to file" + outputFileName, "write to file");
    }
  }

  void flush2() throws CompileError {
    try {
      super.flush();
    } catch (IOException e) {
      throw new CompileError("Failed to write to file" + outputFileName, "write to file");
    }
  }

  void close2() throws CompileError {
    try {
      super.close();
    } catch (IOException e) {
      throw new CompileError("Failed to write to file" + outputFileName, "write to file");
    }
  }

  private String addLineNumber(int line) {
    if (line < 10)
      return "00" + line;

    if (line < 100)
      return "0" + line;

    return String.valueOf(line);
  }
}

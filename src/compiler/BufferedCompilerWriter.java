package compiler;

import compiler.errors.FailedToDeleteFilesError;
import compiler.errors.FileAlreadyExistsError;
import compiler.errors.OutputFileWriteError;

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

  void writeBytecode(ByteCode byteCode, String params) throws OutputFileWriteError {
    writeLine(byteCode.toString() + " " + params, true);
    flush2();
  }

  void writeBytecode(ByteCode byteCode) throws OutputFileWriteError {
    writeBytecode(byteCode, "");
  }

  void writeLine(String string) throws OutputFileWriteError {
    writeLine(string, false);
  }

  void writeNewLine() throws OutputFileWriteError {
    writeLine("");
  }

  int getCurrentLine() {
    return currentLine;
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

  private void writeLine(String string, boolean tab) throws OutputFileWriteError {
    if (!counterFlag) {
      write2(currentLine + ": " + (tab ? '\t' : "") + string);
      newLine2();
      currentLine++;
    } else
      lineCounter++;
  }

  void deleteFiles() throws FailedToDeleteFilesError {
    File file;

    // Close writer
    try {
      close();

      for (String fileName : fileSet) {
        file = new File(fileName);

        // Try to delete file
        if (!file.delete())
          throw new FailedToDeleteFilesError();
      }
    } catch (IOException e) {
      throw new FailedToDeleteFilesError();
    }
  }

  BufferedWriter createFrame(String frameId) throws OutputFileWriteError, FileAlreadyExistsError {
    try {
      String fileName = outputFolder + frameId + ".j";
      File outputFile = new File(fileName);

      if (fileSet.contains(fileName))
        throw new FileAlreadyExistsError(frameId);

      fileSet.add(fileName);

      return new BufferedWriter(new FileWriter(outputFile));
    } catch (IOException e) {
      throw new OutputFileWriteError(frameId + ".j");
    }
  }

  /*
     OVERRIDES
  */

  private void write2(String s) throws OutputFileWriteError {
    try {
      super.write(s);
    } catch (IOException e) {
      throw new OutputFileWriteError(outputFile);
    }
  }

  private void newLine2() throws OutputFileWriteError {
    try {
      super.newLine();
    } catch (IOException e) {
      throw new OutputFileWriteError(outputFile);
    }
  }

  void flush2() throws OutputFileWriteError {
    try {
      super.flush();
    } catch (IOException e) {
      throw new OutputFileWriteError(outputFile);
    }
  }

  void close2() throws OutputFileWriteError {
    try {
      super.close();
    } catch (IOException e) {
      throw new OutputFileWriteError(outputFile);
    }
  }

}

package compiler;

import errors.compiler.FailedToDeleteFilesException;
import errors.compiler.FileAlreadyExistsException;
import errors.compiler.OutputFileWriteException;

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

  void writeBytecode(ByteCode byteCode, String params) throws OutputFileWriteException {
    writeLine(byteCode.toString() + " " + params, true);
    flush2();
  }

  void writeBytecode(ByteCode byteCode) throws OutputFileWriteException {
    writeBytecode(byteCode, "");
  }

  void writeLine(String string) throws OutputFileWriteException {
    writeLine(string, false);
  }

  void writeNewLine() throws OutputFileWriteException {
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

  private void writeLine(String string, boolean tab) throws OutputFileWriteException {
    if (!counterFlag) {
      write2(currentLine + ": " + (tab ? '\t' : "") + string);
      newLine2();
      currentLine++;
    } else
      lineCounter++;
  }

  void deleteFiles() throws FailedToDeleteFilesException {
    File file;

    // Close writer
    try {
      close();

      for (String fileName : fileSet) {
        file = new File(fileName);

        // Try to delete file
        if (!file.delete())
          throw new FailedToDeleteFilesException();
      }
    } catch (IOException e) {
      throw new FailedToDeleteFilesException();
    }
  }

  BufferedWriter createFrame(String frameId) throws OutputFileWriteException, FileAlreadyExistsException {
    try {
      String fileName = outputFolder + frameId + ".j";
      File outputFile = new File(fileName);

      if (fileSet.contains(fileName))
        throw new FileAlreadyExistsException(frameId);

      fileSet.add(fileName);

      return new BufferedWriter(new FileWriter(outputFile));
    } catch (IOException e) {
      throw new OutputFileWriteException(frameId + ".j");
    }
  }

  /*
     OVERRIDES
  */

  private void write2(String s) throws OutputFileWriteException {
    try {
      super.write(s);
    } catch (IOException e) {
      throw new OutputFileWriteException(outputFile);
    }
  }

  private void newLine2() throws OutputFileWriteException {
    try {
      super.newLine();
    } catch (IOException e) {
      throw new OutputFileWriteException(outputFile);
    }
  }

  void flush2() throws OutputFileWriteException {
    try {
      super.flush();
    } catch (IOException e) {
      throw new OutputFileWriteException(outputFile);
    }
  }

  void close2() throws OutputFileWriteException {
    try {
      super.close();
    } catch (IOException e) {
      throw new OutputFileWriteException(outputFile);
    }
  }

}

package compiler;

import errors.compiler.CompilerException;
import errors.compiler.FileAlreadyExistsException;
import errors.compiler.FailedToDeleteFilesException;
import errors.compiler.OutputFileWriteException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

final class CompilerWriter {
  private static final String DEFAULT_OUTPUT_FILE = "output.j";
  private static final String DEFAULT_OUTPUT_FOLDER = ""; // sources root

  private static final String DEFAULT_STATIC_LINK = "4";

  private static final int DEFAULT_LIMIT_LOCALS = 10;
  private static final int DEFAULT_LIMIT_STACK = 256;

  private BufferedWriter writer;

  private HashSet<String> fileSet;

  CompilerWriter() throws CompilerException {
    try {
      File outputFile = new File(DEFAULT_OUTPUT_FILE);

      writer = new BufferedWriter(new FileWriter(outputFile));
      fileSet = new HashSet<>();

      fileSet.add(DEFAULT_OUTPUT_FOLDER + DEFAULT_OUTPUT_FILE);

      writeHeader();
    } catch (IOException e) {
      throw new OutputFileWriteException(DEFAULT_OUTPUT_FILE);
    }
  }

  void write(ByteCode byteCode, String params) throws OutputFileWriteException {
    try {
      writeLine('\t' + byteCode.toString() + " " + params);

      writer.flush();
    } catch (IOException e) {
      throw new OutputFileWriteException(DEFAULT_OUTPUT_FILE);
    }
  }

  void write(ByteCode byteCode) throws OutputFileWriteException {
    write(byteCode, "");
  }

  void loadStaticLink() throws OutputFileWriteException {
    write(ByteCode.LOAD, DEFAULT_STATIC_LINK);
  }

  void beginFrame(CompilerFrame frame) throws CompilerException {
    String frameId = frame.getFrameId();

    // create frame
    write(ByteCode.NEW, frameId);
    write(ByteCode.DUP);
    write(ByteCode.INVOKE_SPECIAL, frameId + "/<init>()V");
    write(ByteCode.DUP);

    // store static link to frame
    write(ByteCode.LOAD, DEFAULT_STATIC_LINK);
    write(ByteCode.PUT_FIELD, frameId + "/sl Ljava/lang/Object;");
    write(ByteCode.STORE, DEFAULT_STATIC_LINK);

    // create class file for frame with no parent
    createFrameClassFile(frameId, null);
  }

  void beginSubFrame(CompilerFrame frame) throws CompilerException {
    String frameId = frame.getFrameId();
    String parentFrameId = frame.getParentFrame().getFrameId();

    // create frame
    write(ByteCode.NEW, frameId);
    write(ByteCode.DUP);

    write(ByteCode.INVOKE_SPECIAL, frameId + "/<init>()V");
    write(ByteCode.DUP);

    // store static link to subFrame
    write(ByteCode.LOAD, DEFAULT_STATIC_LINK);
    write(ByteCode.PUT_FIELD, frameId + "/sl L" + parentFrameId + ';');
    write(ByteCode.STORE, DEFAULT_STATIC_LINK);

    // create class file for frame with parent
    createFrameClassFile(frameId, parentFrameId);
  }

  void endFrame(CompilerFrame frame) throws CompilerException {
    String frameId = frame.getFrameId();

    // retrieve parent frame static link
    write(ByteCode.LOAD, DEFAULT_STATIC_LINK);

    if (frame.getParentFrame() != null)
      write(ByteCode.GET_FIELD, frameId + "/sl L" + frame.getParentFrame().getFrameId() + ';');
    else
      write(ByteCode.GET_FIELD, frameId + "/sl Ljava/lang/Object;");

    write(ByteCode.STORE, DEFAULT_STATIC_LINK);
  }

  void addFieldToFrame(String varIndex, CompilerFrame frame) throws CompilerException {
    String frameId = frame.getFrameId();

    write(ByteCode.PUT_FIELD, frameId + '/' + varIndex + " I");
  }

  void getFieldFromFrame(CompilerFrameField compilerFrameField) throws CompilerException {
    ArrayList<String> subFrames = compilerFrameField.getFrameList();
    String fieldId = compilerFrameField.getFieldId();

    write(ByteCode.LOAD, DEFAULT_STATIC_LINK);

    int i = 0;

    while (i < subFrames.size() - 1) {
      i++;
      write(ByteCode.GET_FIELD, subFrames.get(i - 1) + "/sl " + 'L' + subFrames.get(i) + ';');
    }

    write(ByteCode.GET_FIELD, subFrames.get(i) + '/' + fieldId + " I");
  }

  // Write footer and close file writer
  void close() throws OutputFileWriteException {
    try {
      writeFooter();
      writer.close();
    } catch (IOException e) {
      throw new OutputFileWriteException(DEFAULT_OUTPUT_FILE);
    }
  }

  void deleteFiles() throws FailedToDeleteFilesException {
    File file;

    // Close writer
    try {
      writer.close();

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

  /*
    UTILS
  */
  private void writeLine(String string) throws IOException {
    writer.write(string);
    writer.newLine();
  }

  private void writeLine(BufferedWriter bufferedWriter, String string) throws IOException {
    bufferedWriter.write(string);
    bufferedWriter.newLine();
  }

  private void createFrameClassFile(String frameId, String parentFrame) throws OutputFileWriteException, FileAlreadyExistsException {
    try {
      String fileName = DEFAULT_OUTPUT_FOLDER + frameId + ".j";
      File outputFile = new File(fileName);

      if (fileSet.contains(fileName))
        throw new FileAlreadyExistsException(frameId);

      BufferedWriter frameWriter = new BufferedWriter(new FileWriter(outputFile));
      String staticLink;

      // Format static link
      if (parentFrame != null)
        staticLink = 'L' + parentFrame + ';';
      else
        staticLink = "Ljava/lang/Object;";

      writeLine(frameWriter, ".class " + frameId);
      writeLine(frameWriter, ".super java/lang/Object");
      writeLine(frameWriter, ".field public sl " + staticLink);
      writeLine(frameWriter, ".field public x0 I");

      frameWriter.newLine();

      writeLine(frameWriter, ".method public <init>()V");
      writeLine(frameWriter, '\t' + "aload_0");
      writeLine(frameWriter, '\t' + "invokenonvirtual java/lang/Object/<init>()V");
      writeLine(frameWriter, '\t' + "return");
      writeLine(frameWriter, ".end method");

      frameWriter.close();

      fileSet.add(fileName);
    } catch (IOException e) {
      throw new OutputFileWriteException(frameId + ".j");
    }
  }

  private void writeHeader() throws IOException {
    writeLine(".class public Main");
    writeLine(".super java/lang/Object");

    writer.newLine();

    writeLine(".method public <init>()V");
    writeLine('\t' + "aload_0");
    writeLine('\t' + "invokenonvirtual java/lang/Object/<init>()V");
    writeLine('\t' + "return");
    writeLine(".end method");

    writer.newLine();

    writeLine(".method public static main([Ljava/lang/String;)V");
    writeLine('\t' + ".limit locals " + DEFAULT_LIMIT_LOCALS);
    writeLine('\t' + ".limit stack " + DEFAULT_LIMIT_STACK);

    writer.newLine();

    writeLine("getstatic java/lang/System/out Ljava/io/PrintStream;");

    writeLine('\t' + "aconst_null");
    writeLine('\t' + "astore " + DEFAULT_STATIC_LINK);

    writer.newLine();

    writer.flush();
  }

  private void writeFooter() throws IOException {
    writer.newLine();

    writeLine('\t' + "invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
    writeLine('\t' + "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");

    writer.newLine();

    writeLine('\t' + "return");

    writeLine(".end method");

    writer.flush();
  }
}

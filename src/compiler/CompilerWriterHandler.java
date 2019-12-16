package compiler;

import compiler.errors.CompileError;
import compiler.errors.FailedToDeleteFilesError;
import compiler.errors.FileAlreadyExistsError;
import compiler.errors.OutputFileWriteError;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

final class CompilerWriterHandler {
  private static final String DEFAULT_OUTPUT_FILE = "output.j";
  private static final String DEFAULT_OUTPUT_FOLDER = ""; // sources root

  private static final String DEFAULT_STATIC_LINK = "4";

  private static final int DEFAULT_LIMIT_LOCALS = 10;
  private static final int DEFAULT_LIMIT_STACK = 256;

  private BufferedCompilerWriter writer;

  CompilerWriterHandler() throws CompileError {
    try {
      writer = new BufferedCompilerWriter(DEFAULT_OUTPUT_FILE, DEFAULT_OUTPUT_FOLDER);

      writeHeader();
    } catch (IOException e) {
      throw new OutputFileWriteError(DEFAULT_OUTPUT_FILE);
    }
  }

  void write(ByteCode byteCode, String params) throws OutputFileWriteError {
    writer.writeBytecode(byteCode, params);
  }

  void write(ByteCode byteCode) throws OutputFileWriteError {
    writer.writeBytecode(byteCode);
  }

  void loadStaticLink() throws OutputFileWriteError {
    writer.writeBytecode(ByteCode.LOAD, DEFAULT_STATIC_LINK);
  }

  void beginFrame(Frame frame) throws CompileError {
    String frameId = frame.getFrameId();

    // create frame
    writer.writeBytecode(ByteCode.NEW, frameId);
    writer.writeBytecode(ByteCode.DUP);
    writer.writeBytecode(ByteCode.INVOKE_SPECIAL, frameId + "/<init>()V");
    writer.writeBytecode(ByteCode.DUP);

    // store static link to frame
    loadStaticLink();
    writer.writeBytecode(ByteCode.PUT_FIELD, frameId + "/sl Ljava/lang/Object;");
    writer.writeBytecode(ByteCode.STORE, DEFAULT_STATIC_LINK);

    writer.writeLine("");

    // create class file for frame with no parent
    createFrameClassFile(frameId, null);
  }

  void beginSubFrame(Frame frame) throws CompileError {
    String frameId = frame.getFrameId();
    String parentFrameId = frame.getParentFrame().getFrameId();

    // create frame
    writer.writeBytecode(ByteCode.NEW, frameId);
    writer.writeBytecode(ByteCode.DUP);

    writer.writeBytecode(ByteCode.INVOKE_SPECIAL, frameId + "/<init>()V");
    writer.writeBytecode(ByteCode.DUP);

    // store static link to subFrame
    loadStaticLink();
    writer.writeBytecode(ByteCode.PUT_FIELD, frameId + "/sl L" + parentFrameId + ';');
    writer.writeBytecode(ByteCode.STORE, DEFAULT_STATIC_LINK);

    writer.writeLine("");

    // create class file for frame with parent
    createFrameClassFile(frameId, parentFrameId);
  }

  void endFrame(Frame frame) throws CompileError {
    String frameId = frame.getFrameId();

    writer.writeLine("");

    // retrieve parent frame static link
    loadStaticLink();

    if (frame.getParentFrame() != null)
      writer.writeBytecode(ByteCode.GET_FIELD, frameId + "/sl L" + frame.getParentFrame().getFrameId() + ';');
    else
      writer.writeBytecode(ByteCode.GET_FIELD, frameId + "/sl Ljava/lang/Object;");

    writer.writeBytecode(ByteCode.STORE, DEFAULT_STATIC_LINK);
  }

  void addFrameField(String varIndex, Frame frame) throws CompileError {
    String frameId = frame.getFrameId();

    writer.writeBytecode(ByteCode.PUT_FIELD, frameId + '/' + varIndex + " I");
  }

  void getFrameParentFields(FrameField frameField) throws CompileError {
    ArrayList<String> subFrames = frameField.getFrameList();

    int i = 0;

    while (i < subFrames.size() - 1) {
      i++;
      writer.writeBytecode(ByteCode.GET_FIELD, subFrames.get(i - 1) + "/sl " + 'L' + subFrames.get(i) + ';');
    }
  }

  void getFrameField(FrameField frameField) throws CompileError {
    ArrayList<String> subFrames = frameField.getFrameList();
    String fieldId = frameField.getFieldId();

    loadStaticLink();

    if (subFrames.size() > 1)
      getFrameParentFields(frameField);

    writer.writeBytecode(ByteCode.GET_FIELD, subFrames.get(subFrames.size() - 1) + '/' + fieldId + " I");
  }

  void updateFrameField(FrameField frameField) throws CompileError {
    ArrayList<String> subFrames = frameField.getFrameList();
    String fieldId = frameField.getFieldId();

    writer.writeBytecode(ByteCode.PUT_FIELD, subFrames.get(subFrames.size() - 1) + '/' + fieldId + " I");
  }

  void compare(ByteCode comparisonByteCode) throws OutputFileWriteError {
    int currentLine = writer.getCurrentLine();

    writer.writeBytecode(comparisonByteCode, String.valueOf(currentLine + 3));
    writer.writeBytecode(ByteCode.CONST_0);
    writer.writeBytecode(ByteCode.GOTO, String.valueOf(currentLine + 4));
    writer.writeBytecode(ByteCode.CONST_1);
  }

  int getCurrentLine() {
    return writer.getCurrentLine();
  }

  void startLineCounter() {
    writer.startLineCounter();
  }

  int endLineCounter() {
    return writer.endLineCounter();
  }

  void close() throws OutputFileWriteError {
    writeFooter();
    writer.close2();
  }

  void deleteGeneratedFiles() throws FailedToDeleteFilesError {
    writer.deleteFiles();
  }

  /*
    UTILS
  */

  private void writeFrameLine(BufferedWriter bufferedWriter, String string) throws IOException {
    bufferedWriter.write(string);
    bufferedWriter.newLine();
  }

  private void createFrameClassFile(String frameId, String parentFrame) throws OutputFileWriteError, FileAlreadyExistsError {
    try {
      BufferedWriter frameWriter = writer.createFrame(frameId);
      String staticLink;

      // Format static link
      if (parentFrame != null)
        staticLink = 'L' + parentFrame + ';';
      else
        staticLink = "Ljava/lang/Object;";

      writeFrameLine(frameWriter, ".class " + frameId);
      writeFrameLine(frameWriter, ".super java/lang/Object");
      writeFrameLine(frameWriter, ".field public sl " + staticLink);
      writeFrameLine(frameWriter, ".field public x0 I");

      writeFrameLine(frameWriter, "");

      writeFrameLine(frameWriter, ".method public <init>()V");
      writeFrameLine(frameWriter, '\t' + "aload_0");
      writeFrameLine(frameWriter, '\t' + "invokenonvirtual java/lang/Object/<init>()V");
      writeFrameLine(frameWriter, '\t' + "return");
      writeFrameLine(frameWriter, ".end method");

      frameWriter.close();
    } catch (IOException e) {
      throw new OutputFileWriteError(frameId + ".j");
    }
  }

  private void writeHeader() throws OutputFileWriteError {
    writer.writeLine(".class public Main");
    writer.writeLine(".super java/lang/Object");

    writer.writeLine("");

    writer.writeLine(".method public <init>()V");
    writer.writeLine('\t' + "aload_0");
    writer.writeLine('\t' + "invokenonvirtual java/lang/Object/<init>()V");
    writer.writeLine('\t' + "return");
    writer.writeLine(".end method");

    writer.writeLine("");

    writer.writeLine(".method public static main([Ljava/lang/String;)V");
    writer.writeLine('\t' + ".limit locals " + DEFAULT_LIMIT_LOCALS);
    writer.writeLine('\t' + ".limit stack " + DEFAULT_LIMIT_STACK);

    writer.writeLine("");

    writer.writeLine("getstatic java/lang/System/out Ljava/io/PrintStream;");

    writer.writeLine('\t' + "aconst_null");
    writer.writeLine('\t' + "astore " + DEFAULT_STATIC_LINK);

    writer.writeLine("");

    writer.flush2();
  }

  private void writeFooter() throws OutputFileWriteError {
    writer.writeLine("");

    writer.writeLine('\t' + "invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
    writer.writeLine('\t' + "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");

    writer.writeLine("");

    writer.writeLine('\t' + "return");

    writer.writeLine(".end method");

    writer.flush2();
  }
}

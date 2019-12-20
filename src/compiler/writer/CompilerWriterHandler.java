package compiler.writer;

import compiler.ByteCode;
import compiler.Compiler;
import compiler.CompilerType;
import compiler.errors.CompileError;
import compiler.frame.Frame;
import compiler.frame.FrameField;
import compiler.frame.FrameFunctionField;
import compiler.frame.FrameStructField;
import node.ASTNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.Map.Entry;

public final class CompilerWriterHandler {
  private static final String OUTPUT_FOLDER = "generated"; // sources root
  private static final String DEFAULT_STATIC_LINK = "4";

  private static final int DEFAULT_LIMIT_LOCALS = 10;
  private static final int DEFAULT_LIMIT_STACK = 256;

  private Compiler compiler;
  private HashSet<String> fileNames;

  private BufferedCompilerWriter mainWriter;
  private BufferedCompilerWriter tempWriter;

  public CompilerWriterHandler(Compiler compiler) throws CompileError {
    try {
      File mainFile = createFile("main.j");
      mainWriter = new BufferedCompilerWriter(mainFile);

      fileNames = new HashSet<>();

      writeMainHeader();
    } catch (IOException e) {
      throw new CompileError("Failed to write to main file", "create main program file");
    }
  }

  public void write(ByteCode byteCode, String params) throws CompileError {
    mainWriter.writeBytecode(byteCode, params);
  }

  public void write(ByteCode byteCode) throws CompileError {
    mainWriter.writeBytecode(byteCode);
  }

  public void loadStaticLink() throws CompileError {
    mainWriter.writeBytecode(ByteCode.LOAD, DEFAULT_STATIC_LINK);
  }

  public void beginFrame(Frame frame) throws CompileError {
    String frameId = frame.getFrameId();

    // create frame
    mainWriter.writeBytecode(ByteCode.NEW, frameId);
    mainWriter.writeBytecode(ByteCode.DUP);

    mainWriter.writeBytecode(ByteCode.INVOKE_SPECIAL, frameId + "/<init>()V");
    mainWriter.writeBytecode(ByteCode.DUP);

    // store static link to frame
    loadStaticLink();

    if (frame.hasParent())
      mainWriter.writeBytecode(ByteCode.PUT_FIELD, frameId + "/sl L" + frame.getParentFrame().getFrameId() + ';');
    else
      mainWriter.writeBytecode(ByteCode.PUT_FIELD, frameId + "/sl Ljava/lang/Object;");

    mainWriter.writeBytecode(ByteCode.STORE, DEFAULT_STATIC_LINK);

    mainWriter.writeLine("");
  }

  public void endFrame(Frame frame) throws CompileError {
    String frameId = frame.getFrameId();

    mainWriter.writeLine("");

    // retrieve parent frame static link
    loadStaticLink();

    if (frame.getParentFrame() != null)
      mainWriter.writeBytecode(ByteCode.GET_FIELD, frameId + "/sl L" + frame.getParentFrame().getFrameId() + ';');
    else
      mainWriter.writeBytecode(ByteCode.GET_FIELD, frameId + "/sl Ljava/lang/Object;");

    mainWriter.writeBytecode(ByteCode.STORE, DEFAULT_STATIC_LINK);

    createFrameFiles(frame);
  }

  public void getFrameParentFields(FrameField frameField) throws CompileError {
    ArrayList<String> subFrames = frameField.getFrameList();

    int i = 0;

    while (i < subFrames.size() - 1) {
      i++;
      mainWriter.writeBytecode(ByteCode.GET_FIELD, subFrames.get(i - 1) + "/sl " + 'L' + subFrames.get(i) + ';');
    }
  }

  public String getFrameField(FrameField frameField) throws CompileError {
    ArrayList<String> subFrames = frameField.getFrameList();
    String fieldId = frameField.getFieldId();

    loadStaticLink();

    if (subFrames.size() > 1)
      getFrameParentFields(frameField);

    String frameId = subFrames.get(subFrames.size() - 1);

    mainWriter.writeBytecode(ByteCode.GET_FIELD, frameId + '/' + fieldId + " I");

    return frameId;
  }

  public void putFrameField(FrameField frameField) throws CompileError {
    ArrayList<String> subFrames = frameField.getFrameList();
    String fieldId = frameField.getFieldId();

    CompilerType type = frameField.getType();
    String frameId = subFrames.get(subFrames.size() - 1);

    if (frameField instanceof FrameStructField)
      mainWriter.writeBytecode(ByteCode.PUT_FIELD, frameId + '/' + fieldId + " " + litTypeToString(type));
    else
      mainWriter.writeBytecode(ByteCode.PUT_FIELD, frameId + '/' + fieldId + " L" + frameId + "struct" + fieldId);
  }

  public void compare(ByteCode comparisonByteCode) throws CompileError {
    mainWriter.writeBytecode(comparisonByteCode, getCurrentLineOffset(3));
    mainWriter.writeBytecode(ByteCode.CONST_0);
    mainWriter.writeBytecode(ByteCode.GOTO, getCurrentLineOffset(4));
    mainWriter.writeBytecode(ByteCode.CONST_1);
  }

  public int getCurrentLine() {
    return mainWriter.getCurrentLine();
  }

  public String getCurrentLineOffset(int offset) {
    return mainWriter.getCurrentLineOffset(offset);
  }

  public void startLineCounter() {
    mainWriter.startLineCounter();
  }

  public int endLineCounter() {
    return mainWriter.endLineCounter();
  }

  public void close() throws CompileError {
    writeMainFooter();
    mainWriter.close2();
  }

  public void deleteGeneratedFiles() throws CompileError {
    File file;

    for (String fileName : fileNames) {
      file = new File(fileName);

      if (!file.delete())
        throw new CompileError("Failed to delete file " + fileName, "delete all files");
    }
  }

  // STRUCTS-------------------------------------------------------------------------------------------------------

  public void getFrameStructField(FrameStructField field, String structFieldId) throws CompileError {
    String frameId = getFrameField(field);
    CompilerType type = field.getStructFieldType(structFieldId);

    mainWriter.writeBytecode(ByteCode.GET_FIELD, frameId + "struct" + field.getFieldId() + '/' + structFieldId + " " + litTypeToString(type));
  }

  public void putFrameStructField(FrameStructField field, String structFieldId) throws CompileError {
    String frameId = getFrameField(field);
    CompilerType type = field.getStructFieldType(structFieldId);

    mainWriter.writeBytecode(ByteCode.PUT_FIELD, frameId + "struct" + field.getFieldId() + '/' + structFieldId + " " + litTypeToString(type));
  }

  // FUNCTIONS ----------------------------------------------------------------------------------------------------


  // PRINT --------------------------------------------------------------------------------------------------------

  public void startPrint() throws CompileError {
    mainWriter.writeLine("getstatic java/lang/System/out Ljava/io/PrintStream;");
  }

  public void endPrint(CompilerType type) throws CompileError {
    mainWriter.writeLine("invokestatic java/lang/String/valueOf(" + litTypeToString(type) + ")Ljava/lang/String;");
    mainWriter.writeLine("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
  }

  /*
    UTILS
  */
  private void createFrameFiles(Frame frame) throws CompileError {
    try {
      String frameId = frame.getFrameId();

      BufferedCompilerWriter writer = new BufferedCompilerWriter(createFile(frameId));

      writer.writeLine(".class " + frameId);
      writer.writeLine(".super java/lang/Object");

      // Format the static link
      if (frame.hasParent())
        writer.writeLine(".field public sl L" + frame.getParentFrame().getFrameId() + ';');
      else
        writer.writeLine(".field public sl Ljava/lang/Object;");

      writer.writeLine("");

      for (FrameField field : frame.getFields()) {
        if (field.getType().isLit())
          writer.writeLine(".field public " + field.getFieldId() + " " + litTypeToString(field.getType()));

        if (field.getType() == CompilerType.STRUCT)
          writer.writeLine(".field public " + field.getFieldId() + frameId + "struct" + field.getFieldId() + ';');
      }

      writeDefaultConstructor(writer);

      // Create struct files
      for (FrameField field : frame.getFields()) {
        if (field instanceof FrameStructField) {
          String className = frameId + "struct" + field.getFieldId();

          writer = new BufferedCompilerWriter(createFile(className));

          writer.writeLine(".class " + className);
          writer.writeLine(".super java/lang/Object");

          for (Entry<String, CompilerType> entry : ((FrameStructField) field).getEntries().entrySet())
            writer.writeLine(".field public " + entry.getKey() + " " + litTypeToString(entry.getValue()));

          writeDefaultConstructor(writer);

          writer.close2();
          continue;
        }

        if (field instanceof FrameFunctionField) {
          ASTNode node = ((FrameFunctionField) field).getNode();

          tempWriter = mainWriter;
          mainWriter = new BufferedCompilerWriter(createFile(frameId));

          StringBuilder args = new StringBuilder();

          for (CompilerType compilerType : ((FrameFunctionField) field).getParams())
            args.append(litTypeToString(compilerType));

          CompilerType compilerReturnType = ((FrameFunctionField) field).getReturnType();

          String returnType = compilerReturnType == CompilerType.VOID
              ? "V"
              : litTypeToString(compilerReturnType);

          mainWriter.writeLine("");

          mainWriter.writeLine(".method public static main(" + args + ")" + returnType);
          node.compile(compiler);
          mainWriter.writeLine(".end method");

          mainWriter.close2();
          mainWriter = tempWriter;
        }
      }

      writer.close2();
    } catch (IOException e) {
      throw new CompileError("Failed to write file for frame " + frame.getFrameId(), "create frame files");
    }
  }

  private void writeMainHeader() throws CompileError {
    mainWriter.writeLine(".class public Main");
    mainWriter.writeLine(".super java/lang/Object");

    writeDefaultConstructor(mainWriter);

    mainWriter.writeLine(".method public static main([Ljava/lang/String;)V");
    mainWriter.writeLine(".limit locals " + DEFAULT_LIMIT_LOCALS, true);
    mainWriter.writeLine(".limit stack " + DEFAULT_LIMIT_STACK, true);

    mainWriter.writeLine("");

    mainWriter.writeLine("aconst_null", true);
    mainWriter.writeLine("astore " + DEFAULT_STATIC_LINK, true);

    mainWriter.writeLine("");

    mainWriter.flush2();
  }

  private void writeMainFooter() throws CompileError {
    mainWriter.writeLine("");

    mainWriter.writeLine("return", true);

    mainWriter.writeLine(".end method");

    mainWriter.flush2();
  }

  private File createFile(String name) throws CompileError {
    try {
      String filePath = OUTPUT_FOLDER + "/" + name + ".j";
      File file = new File(filePath);

      if (file.exists())
        file.delete();

      file.createNewFile();

      fileNames.add(filePath);

      return file;
    } catch (IOException e) {
      throw new CompileError("Failed to create file " + name, "create file");
    }
  }

  private void writeDefaultConstructor(BufferedCompilerWriter writer) throws CompileError {
    writer.writeLine("");
    writer.writeLine(".method public <init>()V");
    writer.writeLine("aload_0", true);
    writer.writeLine("invokespecial java/lang/Object/<init>()V", true);
    writer.writeLine("return", true);
    writer.writeLine(".end method");
    writer.writeLine("");
  }

  private String litTypeToString(CompilerType type) throws CompileError {
    if (type == CompilerType.INT || type == CompilerType.BOOL)
      return "I";

    if (type == CompilerType.STRING)
      return "Ljava/lang/String;";

    throw new CompileError("Type is not a literal", "extract string value from type");
  }
}

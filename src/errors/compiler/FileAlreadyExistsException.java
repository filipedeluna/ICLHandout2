package errors.compiler;

public class FileAlreadyExistsException extends CompilerException {
    public FileAlreadyExistsException(String frameId) {
        super("File " + frameId + "already exists.");
    }
}
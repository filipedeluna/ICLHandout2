package errors.compiler;

public class FailedToDeleteFilesException extends CompilerException {
    public FailedToDeleteFilesException() {
        super("Failed to delete leftover files.");
    }
}
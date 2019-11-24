package errors.compiler;

public class EmptyCompilerRegisterException extends CompilerException {
    public EmptyCompilerRegisterException() {
        super("No variable found in temporary compiler register.");
    }
}
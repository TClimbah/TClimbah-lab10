public class EmptyFileException extends Exception {
    public EmptyFileException(String path) {
        super(path + " was empty");
    }
}
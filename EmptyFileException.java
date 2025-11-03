public class EmptyFileException extends Exception {
    public EmptyFileException(String path) {
        super("The file you have chosen is empty: " + path);
    }
}
package ApProject_OnlineShop.exception;

public class FileCantBeSavedException extends Exception {
    public FileCantBeSavedException() {
        super("file could not be saved successfully.");
    }
}

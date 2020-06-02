package ApProject_OnlineShop.exception;

public class FileCantBeDeletedException extends Exception {
    public FileCantBeDeletedException() {
        super("file could not be deleted.");
    }
}

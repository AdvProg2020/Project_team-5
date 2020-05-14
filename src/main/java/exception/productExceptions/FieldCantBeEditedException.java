package exception.productExceptions;

public class FieldCantBeEditedException extends Exception {
    public FieldCantBeEditedException(String field, String reason) {
        super(field + " cant be edited because " + reason + ".");
    }
}

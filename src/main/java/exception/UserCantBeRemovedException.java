package exception;

public class UserCantBeRemovedException extends Exception {
    public UserCantBeRemovedException() {
        System.out.println("this user cant be removed.");
    }
}

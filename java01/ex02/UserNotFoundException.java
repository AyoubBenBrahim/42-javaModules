package java01.ex02;

public class UserNotFoundException extends RuntimeException {

    UserNotFoundException() {
        System.err.println("User not found.");
    }
}

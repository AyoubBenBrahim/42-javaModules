package java01.ex05;

public class MyExceptions {
    public static class UserNotFoundException extends RuntimeException {
    }

    public static class TransactionNotFoundException extends RuntimeException {
        public TransactionNotFoundException(String message) {
            super(message);
        }
    }

    public static class IllegalTransactionException extends RuntimeException {
        public IllegalTransactionException(String message) {
            super(message);
        }
    }
}

package org.borave.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public static class UsernameAlreadyExistsException extends UserException {
        public UsernameAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class EmailAlreadyExistsException extends UserException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }
    public static class UserNotFoundException extends UserException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}

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
    public static class UserNotExists extends UserException {
        public UserNotExists(String message) {
            super(message);
        }
    }
}

package org.borave.exception;

public class ProfileException extends RuntimeException {
    public ProfileException(String message) {
        super(message);
    }

    public static class UsernameAlreadyExistsException extends ProfileException {
        public UsernameAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class EmailAlreadyExistsException extends ProfileException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }
    public static class ProfileNotFoundException extends ProfileException {
        public ProfileNotFoundException(String message) {
            super(message);
        }
    }

    public static class SameProfileException extends ProfileException {
        public SameProfileException(String message) {
            super(message);
        }
    }

    public static class FriendRequestAlreadySentException extends ProfileException {
        public FriendRequestAlreadySentException(String message) {
            super(message);
        }
    }
}

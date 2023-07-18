package org.borave.exception;

public class PostException  extends RuntimeException {
    public PostException(String message) {
        super(message);
    }

    public static class PostNotCreated extends ProfileException {
        public PostNotCreated(String message) {
            super(message);
        }
    }
}

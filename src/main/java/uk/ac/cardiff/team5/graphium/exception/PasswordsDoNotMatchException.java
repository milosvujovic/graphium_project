package uk.ac.cardiff.team5.graphium.exception;

public class PasswordsDoNotMatchException extends Exception {
    public PasswordsDoNotMatchException(String message) {
        super(message);
    }
}

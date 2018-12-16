package me.mocha.minisns.exception;

public class ConflictException extends ApplicationException {

    public ConflictException(String msg, int status) {
        super(msg, status);
    }
}

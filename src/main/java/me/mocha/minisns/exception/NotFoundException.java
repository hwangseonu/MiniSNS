package me.mocha.minisns.exception;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String msg, int status) {
        super(msg, status);
    }
}

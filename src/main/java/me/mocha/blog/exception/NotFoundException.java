package me.mocha.blog.exception;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String msg, int status) {
        super(msg, status);
    }
}

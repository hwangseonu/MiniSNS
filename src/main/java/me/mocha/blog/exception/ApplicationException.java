package me.mocha.blog.exception;


import lombok.Getter;

public class ApplicationException extends RuntimeException {

    @Getter
    protected int status;

    public ApplicationException(String msg, int status) {
        super(msg);
        this.status = status;
    }

}

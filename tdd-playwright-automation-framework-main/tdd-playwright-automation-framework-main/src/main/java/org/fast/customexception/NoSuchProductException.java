package org.fast.customexception;

public class NoSuchProductException extends RuntimeException{
    public NoSuchProductException(String msg) {
        super(msg);
    }
}

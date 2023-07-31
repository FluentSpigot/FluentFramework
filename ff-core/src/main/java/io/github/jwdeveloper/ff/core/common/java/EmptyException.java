package io.github.jwdeveloper.ff.core.common.java;

public class EmptyException extends RuntimeException {
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
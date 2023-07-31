package io.github.jwdeveloper.ff.extension.gameobject.neww.api.exception;

public class ComponentException extends RuntimeException
{
    public ComponentException() {
    }

    public ComponentException(Class<?> type, String location) {
        super("Component of type: "+type+" not found while looking at "+location);
    }

    public ComponentException(String message) {
        super(message);
    }

    public ComponentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentException(Throwable cause) {
        super(cause);
    }

    public ComponentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}


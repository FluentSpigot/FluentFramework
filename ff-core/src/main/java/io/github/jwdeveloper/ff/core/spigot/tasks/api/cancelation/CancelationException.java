package io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation;

public class CancelationException extends RuntimeException
{
    public CancelationException() {
    }

    public CancelationException(String message) {
        super(message);
    }

    public CancelationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancelationException(Throwable cause) {
        super(cause);
    }

    public CancelationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

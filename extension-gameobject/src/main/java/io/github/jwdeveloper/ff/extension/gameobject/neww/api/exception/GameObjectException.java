package io.github.jwdeveloper.ff.extension.gameobject.neww.api.exception;

public class GameObjectException extends RuntimeException
{

    public GameObjectException() {
    }

    public GameObjectException(String message) {
        super(message);
    }

    public GameObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameObjectException(Throwable cause) {
        super(cause);
    }

    public GameObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

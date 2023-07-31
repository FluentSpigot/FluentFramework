package io.github.jwdeveloper.ff.extension.gameobject.neww.api.exception;

public class GameObjectEngineException  extends RuntimeException
{
    public GameObjectEngineException() {
    }

    public GameObjectEngineException(String message) {
        super(message);
    }

    public GameObjectEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameObjectEngineException(Throwable cause) {
        super(cause);
    }

    public GameObjectEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package io.github.jwdeveloper.ff.core.common;

import lombok.Data;

@Data
public class ActionResult<T> {
    private boolean success = true;
    private T content;
    private String message;

    public ActionResult() {
    }

    protected ActionResult(T object) {
        this.content = object;
    }

    protected ActionResult(T object, boolean success) {
        this(object);
        this.success = success;
    }

    protected ActionResult(T object, boolean status, String message) {
        this(object, status);
        this.message = message;
    }

    public boolean isFailed() {
        return !isSuccess();
    }

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasContent() {
        return content != null;
    }

    public static <T> ActionResult<T> success() {
        return new ActionResult<>(null, true);
    }

    public <Output> ActionResult<Output> cast(Output output) {
        return new ActionResult<>(output, this.isSuccess(), this.getMessage());
    }

    public <Output> ActionResult<Output> cast() {
        return new ActionResult<>(null, this.isSuccess(), this.getMessage());
    }

    public static <Input, Output> ActionResult<Output> cast(ActionResult<Input> action, Output output) {
        return new ActionResult<>(output, action.isSuccess(), action.getMessage());
    }

    public static <T> ActionResult<T> success(T payload) {
        return new ActionResult<>(payload, true);
    }

    public static <T> ActionResult<T> success(T payload, String message) {
        return new ActionResult<>(payload, true, message);
    }

    public static <T> ActionResult<T> failed() {
        return new ActionResult<>(null, false);
    }

    public static <T> ActionResult<T> failed(String message) {
        return new ActionResult<>(null, false, message);
    }

    public static <T> ActionResult<T> failed(T target, String message) {
        return new ActionResult<>(target, false, message);
    }

}

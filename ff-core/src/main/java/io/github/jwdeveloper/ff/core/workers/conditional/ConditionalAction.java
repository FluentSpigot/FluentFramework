package io.github.jwdeveloper.ff.core.workers.conditional;

public interface ConditionalAction {

    /**
     * Invoked after action is started
     */
    void onStart();

    /**
     * @return when true, condition is meet and action is done
     */
    boolean onCondition();

    /**
     * Action compleated sucessfully
     */
    void onDone();

    /**
     * Action stoped due to timeout
     */
    void onCancel();

    /**
     * Invoked when onDone or onCancled
     */
    void onEnd();
}
/**
 * 01eccad1-1ccf-422c-9a19-2c7fd1f04e62
 */

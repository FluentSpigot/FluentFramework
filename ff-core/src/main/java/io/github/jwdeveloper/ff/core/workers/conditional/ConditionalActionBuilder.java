package io.github.jwdeveloper.ff.core.workers.conditional;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConditionalActionBuilder {
    private Supplier<Boolean> onCondition;

    private Consumer<Void> onStart;

    private Consumer<Void> onDone;

    private Consumer<Void> onCancel;

    private Consumer<Void> onEnd;

    private final FluentTaskFactory taskFactory;

    public ConditionalActionBuilder(FluentTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
        onCondition = () -> {
            return true;
        };
        onStart = (x) -> {
        };
        onDone = (x) -> {
        };
        onCancel = (x) -> {
        };
    }

    public static ConditionalActionBuilder create(FluentTaskFactory factory) {
        return new ConditionalActionBuilder(factory);
    }

    public ConditionalActionBuilder onCondition(Supplier<Boolean> onCondition) {
        this.onCondition = onCondition;
        return this;
    }

    public ConditionalActionBuilder onStart(Consumer<Void> onStart) {
        this.onStart = onStart;
        return this;
    }

    public ConditionalActionBuilder onDone(Consumer<Void> onDone) {
        this.onDone = onDone;
        return this;
    }

    public ConditionalActionBuilder onCancel(Consumer<Void> onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    public ConditionalActionBuilder onEnd(Consumer<Void> onEnd) {
        this.onEnd = onEnd;
        return this;
    }

    public ConditionalWorker run(long timeout) {
        var worker = new ConditionalWorker(new ConditionalAction() {
            @Override
            public void onStart() {
                onStart.accept(null);
            }

            @Override
            public boolean onCondition() {
                return onCondition.get();
            }

            @Override
            public void onDone() {
                onDone.accept(null);
            }

            @Override
            public void onCancel() {
                onCancel.accept(null);
            }

            @Override
            public void onEnd() {
                onEnd.accept(null);
            }
        }, taskFactory);

        worker.start(timeout);
        return worker;
    }
}
/**
 * cdde72c5-f89e-406e-9e5f-d67e8c3544ca
 */

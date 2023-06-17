package io.github.jwdeveloper.ff.core.spigot.events.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventGroup<T> {
    private final List<Consumer<T>> events;

    public EventGroup() {
        events = new ArrayList<>();
    }

    public boolean invoke(T payload) {
        if (isEmpty()) {
            return true;
        }
        for (var event : events) {
            event.accept(payload);
        }
        return true;
    }

    public int size() {
        return events.size();
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public void subscribe(Consumer<T> event) {
        events.add(event);
    }

    public void clear() {
        events.clear();
    }

    public void unsubscribe(Consumer<T> event) {
        events.remove(event);
    }
}

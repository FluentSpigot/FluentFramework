package io.github.jwdeveloper.ff.core.spigot.events.implementation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventGroup<T>
{
    private final List<Consumer<T>> events;

    public EventGroup()
    {
        events = new ArrayList<>();
    }

    public boolean invoke(T payload)
    {
        for(var event : events)
        {
            event.accept(payload);
        }
        return true;
    }

    public void subscribe(Consumer<T> event)
    {
        events.add(event);
    }

    public void unsubscribe(Consumer<T> event)
    {
        events.remove(event);
    }
}

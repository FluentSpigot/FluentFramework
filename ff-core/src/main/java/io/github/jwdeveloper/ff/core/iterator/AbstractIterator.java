package io.github.jwdeveloper.ff.core.iterator;


import java.util.List;

public class AbstractIterator<T> {
    protected final List<T> target;
    protected T defaultValue;
    private T current;
    private int position;

    public AbstractIterator(List<T> target, T defaultValue) {
        this.target = target;
        current = defaultValue;
        position = -1;
        this.defaultValue = defaultValue;
    }

    public T current() {
        return current;
    }

    public T advance() {
        position++;
        if (position >= target.size()) {
            current = defaultValue;
            return defaultValue;
        }
        current = target.get(position);
        return current;
    }

    public T peek(int offset) {
        var index = position + offset;

        if (index >= target.size())
            return defaultValue;

        return target.get(index);
    }


    public boolean isValid() {
        return current != null && !current.equals(defaultValue);
    }


}
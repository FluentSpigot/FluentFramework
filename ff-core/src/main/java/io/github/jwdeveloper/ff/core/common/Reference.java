package io.github.jwdeveloper.ff.core.common;

public class Reference<T>
{
    private T value;


    private boolean hasValue()
    {
        return value != null;
    }

    public void set(T value)
    {
        this.value = value;
    }

    public T get()
    {
        return value;
    }

    public T getOrThrow()
    {
        if(value == null)
        {
            throw new RuntimeException("Reference value is not set");
        }
        return value;
    }

}

package io.github.jwdeveloper.ff.core.common.java;

import java.util.Objects;

public record Pair<A,B>(A key, B value)
{

    public static <X,Y> Pair<X,Y> of(X one, Y two)
    {
        return new Pair<>(one,two);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair pair = (Pair) o;
        if (!Objects.equals(key, pair.key)) {
            return false;
        }

        if (!Objects.equals(value, pair.value)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" + key + ", " + key + "]";
    }
}

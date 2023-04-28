package io.github.jwdeveloper.ff.extension.gui.implementation.observers.ints;

import lombok.Getter;
import lombok.Setter;
import io.github.jwdeveloper.ff.extension.gui.implementation.observers.NotifierOptions;

@Setter
public class IntNotifierOptions extends NotifierOptions
{
    @Getter
    private int yield = 10;

    private int minimum = 0;

    private int maximum = 100;

    private String prefix;

    String getPrefix()
    {
        return prefix;
    }

    int getMinimum()
    {
        return minimum;
    }

    int getMaximum()
    {
        return maximum;
    }
}

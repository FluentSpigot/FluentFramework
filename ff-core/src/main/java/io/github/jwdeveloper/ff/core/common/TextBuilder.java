package io.github.jwdeveloper.ff.core.common;

import java.util.Arrays;
import java.util.List;

public class TextBuilder<SELF extends TextBuilder<SELF>> {
    protected final StringBuilder builder;

    public TextBuilder() {
        builder = new StringBuilder();
    }

    public SELF text(Object... texts) {
        for (var text : texts) {
            var value = text == null ? "NULL" : text.toString();
            text(value).space();
        }
        return self();
    }

    public SELF text(Object text) {
        builder.append(text);
        return self();
    }

    public SELF space() {
        builder.append(" ");
        return self();
    }

    public SELF space(int count) {
        for (; count > 0; count--) {
            space();
        }
        return self();
    }


    public SELF textNewLine(Object text) {
        return text(text).newLine();
    }

    public SELF textFormat(String pattern, Object... args) {
        return text(String.format(pattern, args));
    }


    public SELF newLine() {
        builder.append(System.lineSeparator());
        return self();
    }

    public SELF bar(String text, int size)
    {
        for (; size >0; size--)
        {
            text(text);
        }
        return self();
    }

    @Override
    public String toString() {
        return builder.toString();
    }


    public List<String> toList()
    {
        return Arrays.stream(toString().split(System.lineSeparator())).toList();
    }

    public String[] toArray()
    {
        return toString().split(System.lineSeparator());
    }

    protected SELF self() {
        return (SELF) this;
    }

}

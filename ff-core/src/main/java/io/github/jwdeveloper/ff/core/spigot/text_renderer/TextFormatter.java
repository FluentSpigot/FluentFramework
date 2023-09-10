package io.github.jwdeveloper.ff.core.spigot.text_renderer;

import io.github.jwdeveloper.ff.core.common.iterator.AbstractIterator;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TextFormatter {
    private final String ENF_OF_FILE = "EOF";

    public List<String> format(String input, int width) {
        var output = new ArrayList<String>();
        var words = splitBySpace(input);

        var iterator = new AbstractIterator<String>(Arrays.stream(words).toList(), ENF_OF_FILE);
        var currentSize = 0;
        var currentLine = new MessageBuilder();

        var world = iterator.advance();
        do {
            if (world.length() > width) {
                output.add(currentLine.toString());
                output.add(world);
                currentLine = new MessageBuilder();
                currentSize = 0;
                world = iterator.advance();
                continue;
            }

            if (currentSize + world.length() > width) {
                output.add(currentLine.toString());
                currentLine = new MessageBuilder();
                currentSize = 0;
                continue;
            }
            currentSize += world.length() + 1;
            currentLine.text(world).space();
            world = iterator.advance();


            if (Objects.equals(world, ENF_OF_FILE)) {
                output.add(currentLine.toString());
                break;
            }
        }
        while ((iterator.isValid()));
        return output;
    }


    private String[] splitBySpace(String text) {
        return text.split("\\s+");
    }

}

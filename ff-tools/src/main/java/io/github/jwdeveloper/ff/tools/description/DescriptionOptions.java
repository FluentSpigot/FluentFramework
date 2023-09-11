package io.github.jwdeveloper.ff.tools.description;
import io.github.jwdeveloper.descrabble.api.DescriptionDecorator;
import io.github.jwdeveloper.descrabble.framework.api.DescrabbleBuilder;
import lombok.Data;
import lombok.Setter;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@Data
public class DescriptionOptions
{
    private Path input;

    private Path output;
    private List<DescriptionDecorator> decorators = new ArrayList<>();
    private final HashMap<String, Object> parameters = new HashMap<>();

    private Consumer<DescrabbleBuilder> onBuild = (e)->{};

    public void addParameter(String key, Object value)
    {
        parameters.put(key,value);
    }

    public void addDecorator(DescriptionDecorator decorator)
    {
        decorators.add(decorator);
    }
}

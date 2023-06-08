package io.github.jwdeveloper.ff.extension.gui.prefab.observers.enums;

import lombok.Setter;
import io.github.jwdeveloper.ff.extension.gui.prefab.observers.NotifierOptions;

import java.util.function.Function;

@Setter
public class EnumNotifierOptions extends NotifierOptions
{
    private Function<String,String> onNameMapping;
    public Function<String, String> getOnNameMapping() {
        return onNameMapping;
    }
}

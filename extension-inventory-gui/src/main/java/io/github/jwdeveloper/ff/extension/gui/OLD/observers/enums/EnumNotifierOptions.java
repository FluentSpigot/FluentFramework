package io.github.jwdeveloper.ff.extension.gui.OLD.observers.enums;

import io.github.jwdeveloper.ff.extension.gui.OLD.observers.NotifierOptions;
import lombok.Setter;

import java.util.function.Function;

@Setter
public class EnumNotifierOptions extends NotifierOptions {
    private Function<String, String> onNameMapping;

    public Function<String, String> getOnNameMapping() {
        return onNameMapping;
    }
}

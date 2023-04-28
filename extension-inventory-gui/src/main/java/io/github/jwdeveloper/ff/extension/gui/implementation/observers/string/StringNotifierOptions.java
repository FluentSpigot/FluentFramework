package io.github.jwdeveloper.ff.extension.gui.implementation.observers.string;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import lombok.Getter;
import lombok.Setter;
import io.github.jwdeveloper.ff.extension.gui.implementation.observers.NotifierOptions;

import java.util.function.Consumer;

@Getter
@Setter
public class StringNotifierOptions extends NotifierOptions {
    private Consumer<TextInputEvent> onTextInput = (e) -> {
    };

    private Consumer<MessageBuilder> message = (e) -> {};
}

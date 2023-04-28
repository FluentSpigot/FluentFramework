package io.github.jwdeveloper.ff.extension.gui.inventory.observers.string;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.inventory.observers.NotifierOptions;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
public class StringNotifierOptions extends NotifierOptions {
    private Consumer<TextInputEvent> onTextInput = (e) -> {
    };

    private Consumer<MessageBuilder> message = (e) -> {};
}

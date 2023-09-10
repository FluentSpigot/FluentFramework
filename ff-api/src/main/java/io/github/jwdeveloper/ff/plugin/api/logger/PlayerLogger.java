package io.github.jwdeveloper.ff.plugin.api.logger;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.core.spigot.messages.text_component.TextComponentBuilder;

public interface PlayerLogger {
    MessageBuilder info(Object... values);

    TextComponentBuilder link(String title, String url);

    MessageBuilder error(Object... values);

    MessageBuilder error(Throwable throwable, Object... values);

    MessageBuilder warning(Object... values);

    MessageBuilder success(Object... values);
}

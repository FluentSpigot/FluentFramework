package io.github.jwdeveloper.ff.extension.items;

import io.github.jwdeveloper.ff.extension.items.api.config.FluentItemApiSettings;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentItemFramework {
    public static FluentApiExtension use(Consumer<FluentItemApiSettings> settingsConsumer) {
        return new FluentItemExtension(settingsConsumer);
    }

    public static FluentApiExtension use() {
        return new FluentItemExtension((e) -> {
        });
    }
}

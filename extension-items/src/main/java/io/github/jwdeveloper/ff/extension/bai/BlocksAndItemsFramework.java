package io.github.jwdeveloper.ff.extension.bai;

import io.github.jwdeveloper.ff.extension.bai.common.FrameworkSettings;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class BlocksAndItemsFramework {
    public static FluentApiExtension use(Consumer<FrameworkSettings> settingsConsumer) {
        return new BlocksAndItemsExtension(settingsConsumer);
    }

    public static FluentApiExtension use() {
        return new BlocksAndItemsExtension(e -> {
        });
    }
}

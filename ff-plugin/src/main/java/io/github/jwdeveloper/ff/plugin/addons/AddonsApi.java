package io.github.jwdeveloper.ff.plugin.addons;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class AddonsApi {
    /**
     * Creates default folder `addons` in the data folder of a plugin
     * then loads all .jar files and inside them looks for classes that implements
     * `FluentApiExtension` interface. Finally instances of those classes are attached to
     * FluentPlugin.useExtension(...)
     */
    public static FluentApiExtension use(Consumer<AddonsOptions> optionsConsumer) {
        return new AddonsExtension(optionsConsumer);
    }
}

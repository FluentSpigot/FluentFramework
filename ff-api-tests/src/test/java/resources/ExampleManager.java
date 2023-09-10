package resources;

import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import lombok.Getter;
import resources.config.TestConfig;

public class ExampleManager
{
    @Getter
    private final FluentConfigFile<TestConfig> options;

    public ExampleManager(FluentConfigFile<TestConfig> options) {
        this.options = options;
    }
}

package resources;

import io.github.jwdeveloper.ff.plugin.implementation.config.options.ConfigOptions;
import lombok.Getter;
import resources.config.TestConfig;

public class ExampleManager
{
    @Getter
    private final ConfigOptions<TestConfig> options;

    public ExampleManager(ConfigOptions<TestConfig> options) {
        this.options = options;
    }
}

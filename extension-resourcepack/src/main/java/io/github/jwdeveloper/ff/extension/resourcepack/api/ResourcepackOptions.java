package io.github.jwdeveloper.ff.extension.resourcepack.api;

import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionOptions;
import lombok.Data;

@Data
public class ResourcepackOptions extends ExtensionOptions
{
    private String configPath = "plugin.resourcepack";

    private String commandName = "resourcepack";

    private String resourcepackUrl;

    private boolean loadOnJoin;
}

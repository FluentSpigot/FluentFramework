package io.github.jwdeveloper.ff.plugin.implementation.config.sections;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlProperty;
import lombok.Data;
import org.bukkit.plugin.Plugin;

@Data
public class DefaultConfigSection
{
    @YamlProperty(path = "plugin")
    private String version;

    public DefaultConfigSection(Plugin plugin)
    {
        this.version = plugin.getDescription().getVersion();
    }

}

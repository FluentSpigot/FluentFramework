package resources.config;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class ConfigSubOptions
{
    @YamlSection(name = "double-nested")
    private ConfigDoubleNestedOptions configDoubleNestedOptions;

    @YamlSection(name = "online-time")
    private float onlineTime;

    @YamlSection(name = "last-session")
    private String lastSession;
}

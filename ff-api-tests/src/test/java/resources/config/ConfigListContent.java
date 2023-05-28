package resources.config;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class ConfigListContent
{
    @YamlSection(name = "name")
    public String name;

    @YamlSection(name = "last-name")
    public String lastName;
}

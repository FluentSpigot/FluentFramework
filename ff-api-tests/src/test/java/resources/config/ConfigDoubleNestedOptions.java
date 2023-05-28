package resources.config;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class ConfigDoubleNestedOptions
{
    @YamlSection(name = "desc")
    private String description;
}

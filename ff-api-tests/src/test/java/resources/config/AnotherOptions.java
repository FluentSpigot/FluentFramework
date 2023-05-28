package resources.config;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class AnotherOptions
{
    @YamlSection(name = "say-my-name")
    private String sayMyName;
}

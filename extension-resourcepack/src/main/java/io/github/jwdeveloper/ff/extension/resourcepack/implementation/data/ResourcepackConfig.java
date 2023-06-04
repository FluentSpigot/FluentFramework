package io.github.jwdeveloper.ff.extension.resourcepack.implementation.data;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class ResourcepackConfig
{
    @YamlSection( name = "url", description =  """
            If you need to replace default resourcepack with your custom one
            set this to link of you resourcepack
            ! after plugin update make sure your custom resourcepack is compatible !""")
    private String url;

    @YamlSection( name = "load-on-player-join")
    private boolean loadOnJoin;
}

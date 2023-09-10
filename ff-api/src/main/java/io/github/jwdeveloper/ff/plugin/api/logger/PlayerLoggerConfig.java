package io.github.jwdeveloper.ff.plugin.api.logger;
import io.github.jwdeveloper.ff.core.common.ColorPallet;
import lombok.Data;

@Data
public class PlayerLoggerConfig
{
    private String prefix;

    private ColorPallet colorPallet;
}

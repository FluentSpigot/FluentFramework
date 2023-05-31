package io.github.jwdeveloper.ff.extension.updater.api.config;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class UpdaterConfig {
    @YamlSection(name = "force-update", description = "if there is new update, it is downloaded and installed")
    private boolean forceUpdate;

    @YamlSection(name = "check-update")
    private CheckUpdateConfig checkUpdateConfig;
}

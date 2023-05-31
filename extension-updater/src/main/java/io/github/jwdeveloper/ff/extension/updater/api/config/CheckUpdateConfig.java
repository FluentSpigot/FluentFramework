package io.github.jwdeveloper.ff.extension.updater.api.config;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class CheckUpdateConfig
{
    @YamlSection(name = "enabled", description = "checking if there is new update")
    private boolean checkUpdate = true;

    @YamlSection(name = "check-every-minutes", description = "Checking update every X minutes")
    private int checkUpdateMinutes;

    @YamlSection(name = "send-update-message-to-console", description = "checking if there is new update")
    private boolean checkUpdateMessage;

    @YamlSection(name = "send-update-message-to-op", description = "Sends message to op players when update is ready")
    private boolean checkUpdateOpMessage;

}

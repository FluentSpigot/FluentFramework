package io.github.jwdeveloper.ff.extension.updater.api.options;

import lombok.Data;

@Data
public class UpdaterOptions
{
    private boolean forceUpdate;
    private boolean checkUpdateOnStart = true;
    private String commandName = "update";
}

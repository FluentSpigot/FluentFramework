package io.github.jwdeveloper.ff.extension.files.implementation.config;


import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class FluentFilesConfig
{
    @YamlSection(name = "plugin.files.auto-save")
    public boolean autoSave = true;

    @YamlSection(name = "plugin.files.saving-frequency",description =
            """
            Determinate how frequent data is saved to files, value in minutes
             """)
    private String savingFrequency;



    private String savingPath;
}

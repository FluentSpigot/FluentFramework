package io.github.jwdeveloper.ff.color_picker.implementation;

import io.github.jwdeveloper.ff.color_picker.implementation.commands.ColorPickerCommand;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class ColorPickerExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.container().registerSigleton(ColorPicker.class);
        builder.defaultCommand().subCommandsConfig(subCommandConfig ->
        {
            subCommandConfig.addSubCommand(ColorPickerCommand.getCommand());
        });
    }
}

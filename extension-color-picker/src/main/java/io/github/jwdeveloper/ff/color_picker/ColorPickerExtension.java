package io.github.jwdeveloper.ff.color_picker;

import io.github.jwdeveloper.ff.color_picker.implementation.ColorPicker;
import io.github.jwdeveloper.ff.color_picker.implementation.ColorsRepository;
import io.github.jwdeveloper.ff.color_picker.implementation.ColorsService;
import io.github.jwdeveloper.ff.color_picker.implementation.commands.ColorPickerCommand;
import io.github.jwdeveloper.ff.color_picker.implementation.gui.ColorPickerWidget;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class ColorPickerExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        var container = builder.container();
        container.registerSigleton(ColorPicker.class);
        container.registerSigleton(ColorsService.class);
        container.registerSigleton(ColorsRepository.class);
        container.registerSigleton(ColorPickerWidget.class);
        builder.defaultCommand()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(ColorPickerCommand.getCommand());
                });
    }
}

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
        container.registerSingleton(ColorPicker.class);
        container.registerSingleton(ColorsService.class);
        container.registerSingleton(ColorsRepository.class);
        container.registerSingleton(ColorPickerWidget.class);
        builder.mainCommand().addSubCommand("colors", ColorPickerCommand::getCommand);

    }
}

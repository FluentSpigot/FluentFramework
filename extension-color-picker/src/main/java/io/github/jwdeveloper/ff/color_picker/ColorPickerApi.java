package io.github.jwdeveloper.ff.color_picker;

import io.github.jwdeveloper.ff.color_picker.implementation.ColorPickerExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class ColorPickerApi
{
    public static FluentApiExtension use()
    {
          return new ColorPickerExtension();
    }
}

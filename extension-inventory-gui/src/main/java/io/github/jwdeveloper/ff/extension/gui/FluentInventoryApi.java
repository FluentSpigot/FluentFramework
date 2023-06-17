package io.github.jwdeveloper.ff.extension.gui;

import io.github.jwdeveloper.ff.extension.gui.implementation.FluentInventoryExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class FluentInventoryApi {


    public static FluentApiExtension use()
    {
        return new FluentInventoryExtension();
    }

}

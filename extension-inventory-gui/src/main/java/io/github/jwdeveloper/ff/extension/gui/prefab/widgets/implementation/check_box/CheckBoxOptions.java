package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.check_box;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.WidgetOptions;
import lombok.Setter;
import org.bukkit.Material;

@Setter
public class CheckBoxOptions extends WidgetOptions {
    Material enableMaterial;

    Material disableMaterial;

    String enabled;

    String disabled;

    String prefix;

    String infoMessage;

    Observer<Boolean> itemObserver;
}

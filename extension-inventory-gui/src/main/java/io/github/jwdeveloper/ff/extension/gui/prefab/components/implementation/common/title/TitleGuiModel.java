package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.title;

import lombok.Data;

import java.util.function.Supplier;

@Data
public class TitleGuiModel {
    private Supplier<String> titleSupplier;
    private String tag;
    private boolean cached;
    private boolean disabled;
    private int piority;
}

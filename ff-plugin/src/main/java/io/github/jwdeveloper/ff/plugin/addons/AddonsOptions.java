package io.github.jwdeveloper.ff.plugin.addons;

import lombok.Data;

import java.util.function.Consumer;

@Data
public class AddonsOptions {
    /**
     * default path for addons
     * <server>/plugins/<plugin>/addons
     */
    private String addonsPath = "addons";

    /**
     * if an addon is not loaded while plugin will be disabled
     */
    private boolean mustLoadAllAddons = true;

    /**
     * triggered when addon is loaded,
     * input is path to addon
     */
    private Consumer<String> onAddonLoaded = (x) -> {
    };
}

package io.github.jwdeveloper.ff.extension.gui;

import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.implementation.FluentInventoryExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class FluentInventoryApi {

    /**
     * obtaining instance
     * InventoryApi = FluentApi.container().findInstance(FluentInventoryApi.API);
     */
    public static Class<InventoryApi> API = InventoryApi.class;

    public static FluentApiExtension use() {
        return new FluentInventoryExtension();
    }

}

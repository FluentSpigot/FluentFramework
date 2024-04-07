package io.github.jwdeveloper.ff.extension.bai.items.impl.itemstack;

import io.github.jwdeveloper.ff.extension.bai.items.api.itemstack.FluentItemStackMeta;
import lombok.Data;

@Data
public class SimpleItemStackMeta implements FluentItemStackMeta {



    private String uniqueId;

    private String uniqueName;

    private String tag;

    private String pluginVersion;

    private String pluginSession;
}

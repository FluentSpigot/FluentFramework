package io.github.jwdeveloper.ff.extension.bai;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockApi;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingApi;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;

public class BlockAndItemsApi {
    private final FluentItemApi fluentItemApi;
    private final FluentBlockApi blockApi;
    private final FluentCraftingApi craftingApi;

    public BlockAndItemsApi(FluentItemApi fluentItemApi,
                            FluentBlockApi blockApi,
                            FluentCraftingApi craftingApi) {
        this.fluentItemApi = fluentItemApi;
        this.blockApi = blockApi;
        this.craftingApi = craftingApi;
    }

    public FluentItemApi items() {
        return fluentItemApi;
    }

    public FluentBlockApi blocks() {
        return blockApi;
    }

    public FluentCraftingApi craftings() {
        return craftingApi;
    }
}

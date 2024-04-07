package io.github.jwdeveloper.ff.extension.bai;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockApi;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;

public class BlockAndItemsApi
{
    private final FluentItemApi fluentItemApi;

    private final FluentBlockApi blockApi;

    public BlockAndItemsApi(FluentItemApi fluentItemApi, FluentBlockApi blockApi) {
        this.fluentItemApi = fluentItemApi;
        this.blockApi = blockApi;
    }

    public FluentItemApi items()
    {
        return fluentItemApi;
    }

    public FluentBlockApi blocks()
    {
        return blockApi;
    }
}

package io.github.jwdeveloper.ff.extension.bai.blocks.impl.events;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockInstance;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state.FluentBlockState;
import lombok.Getter;

@Getter
public class FluentBlockStateEvent extends FluentBlockEvent
{

    private final FluentBlockState state;

    public FluentBlockStateEvent(FluentBlockInstance fluentBlockInstance, FluentBlockState state) {
        super(fluentBlockInstance);
        this.state = state;
    }


}

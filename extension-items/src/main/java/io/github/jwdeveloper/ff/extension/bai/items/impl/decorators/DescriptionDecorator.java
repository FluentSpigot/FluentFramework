package io.github.jwdeveloper.ff.extension.bai.items.impl.decorators;

import io.github.jwdeveloper.ff.extension.bai.items.api.decorator.FluentItemDecorator;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemCreateEvent;

public class DescriptionDecorator  implements FluentItemDecorator
{

    @Override
    public void onDecorating(FluentItemCreateEvent event) {

        var schema = event.getFluentItem().getSchema();
        var meta = event.getItemStack().getItemMeta();



        event.getItemStack().setItemMeta(meta);
    }
}

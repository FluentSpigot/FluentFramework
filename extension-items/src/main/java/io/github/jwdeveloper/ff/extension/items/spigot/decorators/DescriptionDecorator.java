package io.github.jwdeveloper.ff.extension.items.spigot.decorators;

import io.github.jwdeveloper.ff.extension.items.api.decorator.FluentItemDecorator;
import io.github.jwdeveloper.ff.extension.items.impl.events.FluentItemCreateEvent;

public class DescriptionDecorator  implements FluentItemDecorator
{

    @Override
    public void onDecorating(FluentItemCreateEvent event) {

        var schema = event.getFluentItem().getSchema();
        var meta = event.getItemStack().getItemMeta();



        event.getItemStack().setItemMeta(meta);
    }
}

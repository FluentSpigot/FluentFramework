package io.github.jwdeveloper.ff.extension.gui.inventory.components.pagination;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.events.CreateGuiEvent;
import io.github.jwdeveloper.ff.extension.gui.inventory.FluentButtonUIBuilder;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PaginationBuilder<T>
{
   private Supplier<Collection<T>> type;

   private PaginationComponent.ButtonMapping<T> mapping;

   private EventGroup<CreateGuiEvent> onNextPage;

   private EventGroup<CreateGuiEvent> onBackPage;

    public PaginationBuilder<T> withScrollPosition(boolean isHorizontal)
    {
        return this;
    }

    public PaginationBuilder<T> withSource(Supplier<Collection<T>> type)
    {
       return this;
    }
    public PaginationBuilder<T> withMapping(PaginationComponent.ButtonMapping<T> mapping)
    {
        return this;
    }

    public PaginationBuilder<T> withNextButton(Consumer<FluentButtonUIBuilder> btn)
    {
        return this;
    }

    public PaginationBuilder<T> withBackButton(Consumer<FluentButtonUIBuilder> btn)
    {
        return this;
    }

    public PaginationBuilder<T> onNextPage(Consumer<CreateGuiEvent> eventConsumer)
    {
        onNextPage.subscribe(eventConsumer);
        return this;
    }

    public PaginationBuilder<T> onBackPage(Consumer<CreateGuiEvent> eventConsumer)
    {
        onBackPage.subscribe(eventConsumer);
        return this;
    }
    public PaginationComponent<T> build()
   {
    return new PaginationComponent<>();
   }
}

package io.github.jwdeveloper.ff.extension.gui.inventory;

import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.events.OpenGuiEvent;
import io.github.jwdeveloper.ff.extension.gui.inventory.components.list.ListBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;


public class ScrollableComponent extends InventoryComponent {
    private final ListBuilder<Player> pagination;
    private final InventoryRef ref;
    private final ButtonRef btnRef;

    public ScrollableComponent(ListBuilder<Player> pagination) {
        this.pagination = pagination;
        btnRef = new ButtonRef();
        ref = new InventoryRef();
    }

    @Override
    public void onInitialize(InventoryDecorator d) {
        d.withComponent(pagination
                .configPagination(pb ->
                {
                    pb.withSource(() -> (Collection<Player>) Bukkit.getOnlinePlayers())
                            .withMapping((a, b) ->
                            {

                            })
                            .onNextPage(createGuiEvent ->
                            {

                            })
                            .withBackButton(builder ->
                            {

                            })
                            .onBackPage(createGuiEvent ->
                            {

                            });
                })
                .configSearch(pb ->
                {

                })
                .build());

        d.withTitle("siema");
        d.withEvents(e -> e.onOpen(this::openInventory));
        d.withInventoryReference(ref);
        d.withButton(builder ->
        {
            builder.setDescription(buttonDescriptionInfoBuilder ->
            {
                buttonDescriptionInfoBuilder.addDescriptionLine("siema <toJacek> <siema>");
                buttonDescriptionInfoBuilder.addPlaceHolder("<siema>");
            });
            builder.setReference(btnRef);
        });
    }

    public void openInventory(OpenGuiEvent e) {

    }

    public void refresh() {
        var inv = ref.getOrThrow();

    }
}

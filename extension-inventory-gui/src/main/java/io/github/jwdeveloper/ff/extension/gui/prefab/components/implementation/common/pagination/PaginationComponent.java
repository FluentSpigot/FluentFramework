package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.pagination;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.title.TitleComponent;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.events.CreateGuiEvent;
import io.github.jwdeveloper.ff.extension.gui.api.events.OpenGuiEvent;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import io.github.jwdeveloper.ff.extension.gui.api.references.InventoryRef;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PaginationComponent<T> implements InventoryComponent {

    public static final String CONTENT_BUTTON_TAG = "list-button";
    private static final String TITLE_TAG = "pagination-page";
    private final InventoryRef inventory = new InventoryRef();
    private final EventGroup<Integer> onPageChangeEvent = new EventGroup<>();
    private final List<ButtonUI> contentButtons = new ArrayList<>();
    private TitleComponent titleComponent;
    @Setter
    @Getter
    private Supplier<List<T>> contentSource;
    @Setter
    private ButtonMapping<T> contentMapping;
    @Getter
    private int currentPage;
    @Getter
    private int pageSize;

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {
        decorator.withInventoryReference(inventory);
        decorator.withEvents(e -> e.onRefresh(this::onRefreshEvent));
        decorator.withEvents(e -> e.onCreate(this::createContentButtons));
        decorator.withButton(builder ->
        {
            builder.withTitle("Back");
            builder.withPosition(inventory.get().settings().getHeight() - 1, 3);
            builder.withMaterial(Material.ARROW);
            builder.withOnLeftClick(event -> openPreviousPage());

        });
        decorator.withButton(builder ->
        {
            builder.withTitle("Next");
            builder.withPosition(inventory.get().settings().getHeight() - 1, 5);
            builder.withMaterial(Material.ARROW);
            builder.withOnLeftClick(event -> openNextPage());
        });
        onPageChangeEvent.subscribe(this::onPageEvent);
        titleComponent = decorator.withComponent(new TitleComponent());
        titleComponent.addTitleModel(TITLE_TAG, this::getTitleMessage);
    }

    private String getTitleMessage()
    {
        var builder = new StringBuilder();
        builder.append("Page: ");
        builder.append(getCurrentPage()+1);
        builder.append("/");
        builder.append(getTotalPages(contentSource.get())+1);
        return builder.toString();
    }

    public void openNextPage() {

        if (contentSource == null || contentSource.get() == null) {
            return;
        }

        if (currentPage + 1 > getTotalPages(contentSource.get()))
        {
            return;
        }
        openPage(currentPage + 1);
    }

    public void openPreviousPage() {
        if (currentPage - 1 < 0) {
            return;
        }
        openPage(currentPage - 1);
    }

    public void openPage(int page) {
        currentPage = page;
        onPageChangeEvent.invoke(page);
    }

    private void onRefreshEvent(OpenGuiEvent event)
    {
        var lastPage = getTotalPages(contentSource.get());
        if(getCurrentPage() > lastPage)
        {
            openPage(lastPage);
            return;
        }
        renderButtons(event.getInventory());
    }

    private void onPageEvent(Integer page) {
        renderButtons(inventory.get());
    }

    private void createContentButtons(CreateGuiEvent event) {
        event.getInventory().logger().warning("ListComponent", "Loading buttons");
        var height = event.getInventory().settings().getHeight();
        var width = event.getInventory().settings().getWidth();
        if (height < 3) {
            throw new RuntimeException("Minimal inventory height for list component is 3");
        }
        height = height - 2;
        width = width - 2;
        int yOffset = 1;
        int xOffset = 1;
        for (var i = 0; i < height; i++) {
            for (var j = 0; j < width; j++) {
                var button = new ButtonUI();
                button.setTitle(" ");
                button.setTag(CONTENT_BUTTON_TAG);
                button.setMaterial(Material.GOLD_BLOCK);
                button.setPosition(i + yOffset, j + xOffset);
                contentButtons.add(button);
            }
        }
        event.getInventory().buttons().addButtons(contentButtons);
        pageSize = height * width;
    }

    private void renderButtons(FluentInventory inventory) {
        if (contentMapping == null) {
            inventory.logger().warning("ListComponent", "Mapping not set");
            return;
        }

        var dataSource = this.contentSource.get();
        if (dataSource == null) {
            inventory.logger().warning("ListComponent", "DataSource not set");
            return;
        }

        dataSource = calculateContent(dataSource);
        var dataSourceSize = dataSource.size();
        var buttonsManager = inventory.buttons();
        for (var i = 0; i < contentButtons.size(); i++) {
            var button = contentButtons.get(i);
            if (i >= dataSourceSize) {
                buttonsManager.removeButton(button);
                continue;
            }
            var data = dataSource.get(i);
            contentMapping.onMapping(data, button);
            buttonsManager.addButton(button);
        }
        inventory.logger().warning("ListComponent", "Done");
    }

    private <T> List<T> calculateContent(List<T> content) {
        return content.stream().skip(pageSize * currentPage).limit(pageSize).toList();
    }

    private int getTotalPages(List<T> content)
    {
        var totalItems = content.size();
        int numberOfPages = totalItems / getPageSize();

        if (totalItems % getPageSize() != 0) {
           // numberOfPages++;
        }
        return numberOfPages;
    }
}

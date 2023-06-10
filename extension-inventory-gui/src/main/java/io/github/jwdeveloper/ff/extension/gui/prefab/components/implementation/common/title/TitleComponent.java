package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.title;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.events.OpenGuiEvent;
import io.github.jwdeveloper.ff.extension.gui.api.references.InventoryRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TitleComponent implements InventoryComponent {
    private List<TitleGuiModel> titleGuiModels;
    private Map<String, String> titleCache;
    private final InventoryRef inventoryRef = new InventoryRef();

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {
        decorator.withInventoryReference(inventoryRef);
        decorator.withEvents(e -> e.onRefresh(this::onRefresh));
        titleGuiModels = new ArrayList<>();
        titleCache = new HashMap<>();
    }

    public void addTitleModel(TitleGuiModel titleGuiModel)
    {
        titleGuiModels.add(titleGuiModel);
    }

    public void addTitleModel(String tag,  Supplier<String> supplier)
    {
       addTitleModel(tag, false, supplier);
    }

    public void addTitleModel(String tag, boolean cached, Supplier<String> supplier)
    {
        var pathModel = new TitleGuiModel();
        pathModel.setTag(tag);
        pathModel.setPiority(1);
        pathModel.setCached(cached);
        pathModel.setTitleSupplier(supplier);
        addTitleModel(pathModel);
    }

    public void setTitleModel(String tag,  Supplier<String> supplier)
    {
        var model =titleGuiModels.stream().filter(e -> e.getTag().equalsIgnoreCase(tag)).findFirst();
        if(model.isEmpty())
        {
            return;
        }
        model.get().setTitleSupplier(supplier);
    }

    public void disableTitleModel(String tag)
    {
        var model =titleGuiModels.stream().filter(e -> e.getTag().equalsIgnoreCase(tag)).findFirst();
        if(model.isEmpty())
        {
            return;
        }
        model.get().setDisabled(true);
    }

    public void enableTitleModel(String tag)
    {
        var model =titleGuiModels.stream().filter(e -> e.getTag().equalsIgnoreCase(tag)).findFirst();
        if(model.isEmpty())
        {
            return;
        }
        model.get().setDisabled(false);
    }

    public void usePathTitleModel() {
        var pathModel = new TitleGuiModel();
        pathModel.setTag("path-title");
        pathModel.setPiority(1);
        pathModel.setCached(true);
        pathModel.setTitleSupplier(() ->
        {
            var current = inventoryRef.getOrThrow();
            var names = new ArrayList<String>();
            var currentName = StringUtils.EMPTY;
            while (current != null) {
                currentName = current.settings().getName();
                names.add(currentName);
                current = current.parent();
            }
            var title = new FluentMessages().chat();
            for (var i = names.size() - 1; i >= 0; i--) {
                if (i == 0) {

                    title.text(names.get(i));
                } else {

                    title.text(names.get(i)).text("/");
                }
            }
            return title.toString();
        });
        addTitleModel(pathModel);
    }

    public void refresh()
    {
        buildAndSetTitle();
        inventoryRef.get().refresh();;
    }

    public void buildAndSetTitle()
    {
        var builder = new StringBuilder();
        for(var model : titleGuiModels)
        {
            if(model.isDisabled())
            {
                continue;
            }

            if(titleCache.containsKey(model.getTag()))
            {
                builder.append(titleCache.get(model.getTag())).append(" ");
                continue;
            }

            var title = model.getTitleSupplier().get();
            if(model.isCached())
            {
                titleCache.putIfAbsent(model.getTag(), title);
            }
            builder.append(title).append(" ");
        }
        inventoryRef.get().setTitle(builder.toString());
    }

    private void onRefresh(OpenGuiEvent event) {
        buildAndSetTitle();
    }
}

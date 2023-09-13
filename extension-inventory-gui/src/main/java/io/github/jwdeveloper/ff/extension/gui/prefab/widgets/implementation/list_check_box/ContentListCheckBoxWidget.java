package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list_check_box;


import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.common.java.JavaUtils;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListWidget;
import org.bukkit.ChatColor;

public class ContentListCheckBoxWidget extends ContentListWidget<ListCheckBoxModel> {

    private final ContentListCheckBoxOptions config;

    public ContentListCheckBoxWidget(ContentListCheckBoxOptions options) {
        super(options);
        config = options;
    }

    @Override
    public void onCreate(ButtonBuilder builder, InventoryApi inventoryApi) {
        config.setSelectedItemObserver(ObserverBag.createObserver(new ListCheckBoxModel()));
        config.setContentMapping(this::onDisplayName);
        config.setContentSource(() -> config.listCheckBoxModels);
        config.prefix = JavaUtils.ifNull(config.prefix, StringUtils.EMPTY);
        config.enabled = JavaUtils.ifNull(config.enabled, new MessageBuilder().inBrackets(Emoticons.yes, ChatColor.GREEN, ChatColor.GRAY).toString());
        config.disabled = JavaUtils.ifNull(config.disabled, new MessageBuilder().inBrackets(Emoticons.no, ChatColor.DARK_RED, ChatColor.GRAY).toString());

        builder.withStyleRenderer(styleRendererOptionsDecorator ->
        {
            styleRendererOptionsDecorator.withShiftClickInfo("Enable/Disable");
        });
        builder.withOnShiftClick(this::onShiftClick);

        super.onCreate(builder, inventoryApi);
    }

    public void onShiftClick(ButtonClickEvent event) {
        var selectedItem = getSelectedItem();
        if (selectedItem.getObserver() == null) {
            return;
        }

        var newValue = !selectedItem.getObserver().get();
        selectedItem.getObserver().set(newValue);
    }

    public String onDisplayName(ListCheckBoxModel model)
    {
        var builder= new MessageBuilder();
        if (model.getObserver().get()) {
             builder.text(config.enabled);
        }
        else
        {
            builder.text(config.disabled);
        }
        return builder.space().text(model.getName()).toString();
    }

}

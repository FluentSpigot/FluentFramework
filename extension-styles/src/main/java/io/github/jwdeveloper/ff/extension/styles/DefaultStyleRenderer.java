package io.github.jwdeveloper.ff.extension.styles;

import io.github.jwdeveloper.ff.core.common.ColorPallet;
import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.styles.styles.StyleRenderer;
import io.github.jwdeveloper.ff.extension.styles.styles.StyleRendererOptions;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultStyleRenderer implements StyleRenderer {

    private final FluentMessages messages;
    protected final ColorPallet pallet;
    protected final FluentTranslator translator;
    private final Map<String, List<String>> cachedRenders;
    protected   int barLenght = 20;

    public DefaultStyleRenderer(FluentMessages messages, ColorPallet pallet, FluentTranslator translator) {
        this.messages = messages;
        this.pallet = pallet;
        this.translator = translator;
        cachedRenders = new HashMap<>();
    }

    @Override
    public void render(ItemStack buttonUI, StyleRendererOptions options) {
        if (!options.hasAnyParameter()) {
            return;
        }
        if (options.isUseCache() && cachedRenders.containsKey(options.getCacheId())) {
            setDescription(buttonUI, cachedRenders.get(options.getCacheId()));
            return;
        }

        var resolver = new ParameterResolver(options, translator, pallet);
        var builder = messages.chat();
        var description = createButtonLore(resolver, builder);
        setDescription(buttonUI, description);

        if (options.isUseCache()) {
            cachedRenders.put(options.getCacheId(), description);
        }
    }

    public List<String> createButtonLore(ParameterResolver resolver, MessageBuilder builder) {
        createMainTitle(builder, resolver);

        var descriptions = resolver.getGroup("description");

        if (!descriptions.isEmpty()) {
            createBarTop(builder);
        }
        for (var description : descriptions)
        {
            builder.text(description).newLine();

        }
        createClickInfo(builder, resolver);

        if (!descriptions.isEmpty()) {
            createBarBottom(builder);
        }

        return builder.toList();
    }

    public void createTitle(MessageBuilder builder, String title) {
        builder.color(pallet.getSecondary())
                .color(ChatColor.BOLD)
                .text("[")
                .color(pallet.getPrimary())
                .text(title)
                .color(pallet.getSecondary())
                .color(ChatColor.BOLD)
                .text("]");
    }

    protected void createClickInfo(MessageBuilder builder, ParameterResolver resolver) {
        if (!resolver.hasGroup("click")) {
            return;
        }

        builder.newLine();
        builder.color(pallet.getSecondary()).text(" > ")
                .color(pallet.getSecondary()).color(ChatColor.ITALIC).text(translator.get("gui.base.click-info"))
                .color(pallet.getSecondary()).text(" <").newLine();

        createMid(builder);
        builder.newLine();
        if (resolver.has("click-left")) {
          //  builder.newLine();
            createClickInfoLine(builder, translator.get("gui.base.left-click"), resolver.get("click-left"));
        }

        if (resolver.has("click-right")) {
           // builder.newLine();
            createClickInfoLine(builder, translator.get("gui.base.right-click"), resolver.get("click-right"));

        }

        if (resolver.has("click-shift")) {
          //  builder.newLine();
            createClickInfoLine(builder, translator.get("gui.base.shift-click"), resolver.get("click-shift"));
        }
    }


    protected void createClickInfoLine(MessageBuilder builder, String name, String value) {
        builder.space(1);
        createTitle(builder, name);
        builder.space(1)
                .color(pallet.getPrimary())
                .color(ChatColor.BOLD)
                .text(">")
                .space(1)
                .color(pallet.getTextDark())
                .text(value)
                .newLine();
    }

    public void createMid(MessageBuilder builder) {
        builder.color(pallet.getPrimary())
                .color(ChatColor.BOLD)
                .bar(Emoticons.upperBar, barLenght);
    }

    public void createBarTop(MessageBuilder builder) {
        builder.color(pallet.getPrimary())
                .color(ChatColor.BOLD)
                .bar(Emoticons.boldBar, barLenght)
                .newLine()
                .newLine();
    }

    public void createBarBottom(MessageBuilder builder) {
        builder.color(pallet.getPrimary())
                .color(ChatColor.BOLD)
                .bar(Emoticons.lowerBar, barLenght);
    }

    public void createMainTitle(MessageBuilder builder, ParameterResolver info) {
        if (!info.has("title")) {
            return;
        }
        var title = info.get("title");
        var hasClickInfo = info.hasGroup("click");
        var hasDescription = info.hasGroup("description");
        if (hasClickInfo || hasDescription) {

            var lenght = title.length() / 2;
            var offSet = 2;
            var halfBar = barLenght / 2;
            var location = halfBar - (lenght + offSet);
            builder.space(1);
        } else {
            builder.space(1);
        }
        createTitle(builder, title);
        if (!hasClickInfo && !hasDescription) {
            builder.space(1).newLine();
            builder.text(" ").newLine();
        } else {
            builder.newLine();
        }
    }

    public void setDescription(ItemStack itemStack, List<String> description) {
        var meta = ensureMeta(itemStack);
        if (meta == null) {
            return;
        }

        var finalArray = new ArrayList<String>(description);
        for (var i = 0; i < finalArray.size(); i++) {
            var line = finalArray.get(i);
            finalArray.set(i, ChatColor.WHITE + line);
        }

        meta.setLore(finalArray);
        itemStack.setItemMeta(meta);
    }

    private ItemMeta ensureMeta(ItemStack itemStack) {
        return itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }
}


package io.github.jwdeveloper.ff.extension.gui.inventory.styles;

import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import org.bukkit.ChatColor;

public class FluentButtonStyle
{
    private final ButtonColorSet colorSet;

    public FluentButtonStyle(FluentTranslator translator)
    {
        this.colorSet = getColorSet();
    }

    public ButtonColorSet getColorSet()
    {
        var colorSet = new ButtonColorSet();
        colorSet.setPrimary(ChatColor.AQUA);
        colorSet.setSecondary(ChatColor.DARK_AQUA);
        colorSet.setTextBight("#C6C6C6");
        colorSet.setTextDark("#C6C6C6");
        return colorSet;
    }

}

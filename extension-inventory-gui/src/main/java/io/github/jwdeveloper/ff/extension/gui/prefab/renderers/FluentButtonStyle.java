package io.github.jwdeveloper.ff.extension.gui.prefab.renderers;

import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleColorPallet;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import org.bukkit.ChatColor;

public class FluentButtonStyle
{
    private final StyleColorPallet colorSet;

    public FluentButtonStyle(FluentTranslator translator)
    {
        this.colorSet = getColorSet();
    }

    public static StyleColorPallet getColorSet()
    {
        var colorSet = new StyleColorPallet();
        colorSet.setPrimary(ChatColor.AQUA);
        colorSet.setSecondary(ChatColor.DARK_AQUA);
        colorSet.setTextBight("#C6C6C6");
        colorSet.setTextDark("#C6C6C6");
        return colorSet;
    }

}

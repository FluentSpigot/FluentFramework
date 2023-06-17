package io.github.jwdeveloper.ff.extension.gui.implementation.styles;

import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleColorPallet;
import org.bukkit.ChatColor;

public class ColorPalletFactory {
    public static StyleColorPallet getDefault() {
        var colorSet = new StyleColorPallet();
        colorSet.setPrimary(ChatColor.AQUA);
        colorSet.setSecondary(ChatColor.DARK_AQUA);
        colorSet.setTextBight("#C6C6C6");
        colorSet.setTextDark("#C6C6C6");
        return colorSet;
    }


    public static StyleColorPallet getDark() {
        var colorSet = new StyleColorPallet();
        colorSet.setPrimary(ChatColor.GREEN);
        colorSet.setSecondary(ChatColor.DARK_GREEN);
        colorSet.setTextBight("#C6C6C6");
        colorSet.setTextDark("#C6C6C6");
        return colorSet;
    }
}

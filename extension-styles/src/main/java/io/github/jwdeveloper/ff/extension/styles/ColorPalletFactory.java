package io.github.jwdeveloper.ff.extension.styles;


import io.github.jwdeveloper.ff.core.common.ColorPallet;
import org.bukkit.ChatColor;

public class ColorPalletFactory {
    public static ColorPallet getDefault() {
        var colorSet = new ColorPallet();
        colorSet.setPrimary(ChatColor.AQUA);
        colorSet.setSecondary(ChatColor.DARK_AQUA);
        colorSet.setTextBight("#C6C6C6");
        colorSet.setTextDark("#C6C6C6");
        return colorSet;
    }


    public static ColorPallet getDark() {
        var colorSet = new ColorPallet();
        colorSet.setPrimary(ChatColor.GREEN);
        colorSet.setSecondary(ChatColor.DARK_GREEN);
        colorSet.setTextBight("#C6C6C6");
        colorSet.setTextDark("#C6C6C6");
        return colorSet;
    }
}

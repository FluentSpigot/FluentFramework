package io.github.jwdeveloper.ff.core.common;

import lombok.Data;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ColorPallet {
    private String textBight;
    private String textDark;
    private String primary;
    private String secondary;


    public static ColorPallet DEFAULT()
    {
        var colorSet = new ColorPallet();
        colorSet.setPrimary(ChatColor.AQUA);
        colorSet.setSecondary(ChatColor.DARK_AQUA);
        colorSet.setTextBight("#C6C6C6");
        colorSet.setTextDark("#C6C6C6");
        return colorSet;
    }

    public void setSecondary(String secondary) {
        this.secondary = translateHexCodes(secondary);
    }


    public void setPrimary(String primary) {
        this.primary = translateHexCodes(primary);
    }


    public void setTextBight(String textBight) {
        this.textBight = translateHexCodes(textBight);
    }

    public void setTextDark(String textDark) {
        this.textDark = translateHexCodes(textDark);
    }

    public void setPrimary(ChatColor primary) {
        this.primary = primary.asBungee().toString();
    }

    public void setSecondary(ChatColor secondary) {
        this.secondary = secondary.asBungee().toString();
    }

    public void setTextBight(ChatColor textBight) {
        this.textBight = textBight.asBungee().toString();
    }

    public void setTextDark(ChatColor textDark) {
        this.textDark = textDark.asBungee().toString();
    }


    public static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])");

    public String translateHexCodes(String textToTranslate) {

        textToTranslate = "&" + textToTranslate;
        Matcher matcher = HEX_PATTERN.matcher(textToTranslate);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of("#" + matcher.group(1)).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }
}


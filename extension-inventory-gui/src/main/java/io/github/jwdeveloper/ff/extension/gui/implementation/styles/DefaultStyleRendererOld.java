package io.github.jwdeveloper.ff.extension.gui.implementation.styles;

import io.github.jwdeveloper.ff.core.common.ColorPallet;
import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import org.bukkit.ChatColor;

public class DefaultStyleRendererOld {

    private final FluentMessages simpleMessages;
    private final ColorPallet colorSet;
    private final FluentTranslator translator;
    private String barTop;
    private String barMid;
    private String barBottom;
    private final int barLength = 20;

    public DefaultStyleRendererOld(FluentTranslator translator,
                                   ColorPallet buttonColorSet,
                                   FluentMessages simpleMessages) {
        this.colorSet = buttonColorSet;
        this.translator = translator;
        this.simpleMessages = simpleMessages;
    }

    public String createBarTop() {
        if (barTop == null) {
            barTop = simpleMessages
                    .chat()
                    .color(colorSet.getPrimary())
                    .color(ChatColor.BOLD)
                    .bar(Emoticons.boldBar, barLength)
                    .newLine()
                    .toString();
        }
        return barTop;
    }

    public String createMid() {
        if (barMid == null) {
            barMid = simpleMessages
                    .chat()
                    .color(colorSet.getPrimary())
                    .color(ChatColor.BOLD)
                    .bar(Emoticons.upperBar, barLength)
                    .newLine()
                    .toString();
        }
        return barMid;
    }

    public String createBottom() {
        if (barBottom == null) {
            barBottom = simpleMessages
                    .chat()
                    .color(colorSet.getPrimary())
                    .color(ChatColor.BOLD)
                    .bar(Emoticons.lowerBar, barLength)
                    .newLine()
                    .toString();
        }
        return barBottom;
    }

    public String createTitle(String title) {
        return simpleMessages
                .chat()
                .color(colorSet.getSecondary())
                .color(ChatColor.BOLD)
                .text("[")
                .color(colorSet.getPrimary())
                .text(title)
                .color(colorSet.getSecondary())
                .color(ChatColor.BOLD)
                .text("]")
                .toString();
    }
/*
  public List<String> render(StyleRendererOptions info) {

        var builder = simpleMessages.chat();
        createTitle(builder, info);
        createObserverPlaceholders(builder,info);
        var descResult = render(builder, info);
        var infoResult = createClickInfo(builder, info);
        if (descResult || infoResult) {
            builder.text(createBottom());
        }

        return new ArrayList<>(Arrays.asList(builder.toArray()));
    }

    public void createTitle(MessageBuilder builder, StyleRendererOptions info) {
        if (StringUtils.isNotNullOrEmpty(info.getTitle())) {

            if(info.hasClickInfo() || info.hasDescription())
            {
                var title = info.getTitle();
                var lenght = title.length()/2;
                var offSet =2;
                var halfBar = barLength/2;
                var location = halfBar-(lenght+offSet);
                builder.space(1);
            }
            else
            {
                builder.space(1);
            }
            builder.text(createTitle(info.getTitle()));
            if(!info.hasClickInfo() && !info.hasDescription())
            {
                builder.space(1).newLine();
                builder.text(" ").newLine();
            }
            else
            {
                builder.newLine();
            }
        }
    }

    public void createObserverPlaceholders(MessageBuilder builder, StyleRendererOptions info) {
        if (!info.hasPlaceholders())
        {
           return;
        }
        builder.newLine();
        for(var observer : info.getPlaceHolders())
        {
            builder.text("<observer>").text(observer).newLine();
        }
        builder.newLine();
    }

    public boolean render(MessageBuilder builder, StyleRendererOptions info) {

        if (info.getDescriptionLines().isEmpty())
        {
            return false;
        }

        builder.text(createBarTop());
        builder.newLine();
        var temp = StringUtils.EMPTY;
        var limit = barLength+17;
        for (var line : info.getDescriptionLines()) {
            var text = temp+line;
            if(text.length() > limit)
            {
               var a = StringUtils.EMPTY;
               for(var end = text.length()-1;end >0;end--)
               {
                   var char_ = text.charAt(end);
                   if(char_ == ' ' && end < limit)
                   {
                       text = text.substring(0,end);
                    break;
                   }
                   a = char_ + a;
               }
               temp = a+" ";
            }
            else
            {
                temp = StringUtils.EMPTY;
            }
            builder.text(" ").color(colorSet.getTextBight()).text(text);
            builder.newLine();
        }
        while (!Objects.equals(temp, StringUtils.EMPTY))
        {
            var text = temp;
            if(text.length() > limit)
            {
                var a = StringUtils.EMPTY;
                for(var end = text.length()-1;end >0;end--)
                {
                    var char_ = text.charAt(end);
                    if(char_ == ' ' && end < limit)
                    {
                        text = text.substring(0,end);
                        break;
                    }
                    a = char_ + a;
                }
                temp = a+" ";
            }
            else
            {
                temp = StringUtils.EMPTY;
            }
            builder.text(" ").color(colorSet.getTextBight()).text(text);
            builder.newLine();
        }
        return true;
    }



    public boolean createClickInfo(MessageBuilder builder, StyleRendererOptions info) {
        if (!info.hasClickInfo()) {
            return false;
        }
        builder.newLine();
        builder.color(colorSet.getSecondary()).text(" > ")
                .color(colorSet.getSecondary()).color(ChatColor.ITALIC).text(translator.get("gui.base.click-info"))
                .color(colorSet.getSecondary()).text(" <").newLine();

        builder.text(createMid());
        if (StringUtils.isNotNullOrEmpty(info.getLeftClick())) {
            createClickInfoLine(builder, translator.get("gui.base.left-click"), info.getLeftClick());

        }

        if (StringUtils.isNotNullOrEmpty(info.getRightClick())) {
            builder.newLine();
            createClickInfoLine(builder, translator.get("gui.base.right-click"), info.getRightClick());

        }

        if (StringUtils.isNotNullOrEmpty(info.getShiftClick())) {
            builder.newLine();
            createClickInfoLine(builder, translator.get("gui.base.shift-click"), info.getShiftClick());
        }
        return true;
    }

    public void createClickInfoLine(MessageBuilder builder, String name, String value) {
        var tile = createTitle(name);
        builder.space(2)
                .text(tile)

                .space(1)
                .color(colorSet.getPrimary())
                .color(ChatColor.BOLD)
                .text(">")
                .space(1)
                .color(colorSet.getTextDark())
                .text(value)
                .newLine();
    }*/
}

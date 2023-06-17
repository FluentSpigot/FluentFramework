package io.github.jwdeveloper.ff.extension.gui.OLD;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Data
public class StyleRendererOptionsOld {
    private String tag;
    private String leftClick;
    private String rightClick;
    private String shiftClick;

    private String title;

    private List<String> placeHolders = new ArrayList<>();

    private Function<MessageBuilder, String> descriptionTemplate;

    private Map<String, Function<MessageBuilder, String>> placeHolderParameters;
    private List<String> descriptionLines = new ArrayList<>();

    public boolean hasId() {
        return !StringUtils.isNullOrEmpty(tag);
    }

    public boolean hasDescription() {
        return !descriptionLines.isEmpty();
    }

    public boolean hasPlaceholders() {
        return !placeHolders.isEmpty();
    }


    public void addTemplateParameter(String parameter, Function<MessageBuilder, String> value) {
        placeHolderParameters.put(parameter, value);
    }

    public void addTemplateParameter(String parameter, String value) {
        addTemplateParameter(parameter, (e) -> value);
    }

    public boolean hasClickInfo() {
        return StringUtils.isNotNullOrEmpty(leftClick) ||
                StringUtils.isNotNullOrEmpty(rightClick) ||
                StringUtils.isNotNullOrEmpty(shiftClick);
    }
}

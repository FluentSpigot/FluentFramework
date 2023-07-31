package io.github.jwdeveloper.ff.core.spigot.text_renderer;

public interface TextElementRenderer {
    String getInfo(String title);

    String getWarning(String title);

    String getError(String title);

    String getSuccess(String title);

    String getPrefix(String title);

    String getBar(int length);

    String getText(String text);

    String getListMember(String text);

    String getProperty(String name, Object value);

    String getCheckbox(String name, boolean isChecked);
}

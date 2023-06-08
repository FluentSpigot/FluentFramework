package io.github.jwdeveloper.ff.extension.gui.api.managers.buttons;



import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;

import java.util.List;

public interface ButtonManager {
    void refresh();

    void refresh(ButtonUI buttonUI);

    void refresh(List<ButtonUI> buttonUI);

    ButtonUI[] getButtons();

    ButtonUI getButton(int height, int width);

    ButtonUI getButton(int index);

    ButtonUI getButton(String tag);

    void addButton(ButtonUI button);

    void addButtons(List<ButtonUI> buttons);

    void addButtons(ButtonUI[] buttons);

    void addButton(ButtonUI button, int slotIndex);

    boolean hasButton(int height, int width);

    boolean hasButton(int index);

    void removeButton(int index);

    void removeButton(ButtonUI buttonUI);

    void resize(int height, int width);
}

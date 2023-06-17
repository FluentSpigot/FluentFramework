package io.github.jwdeveloper.ff.extension.gui.implementation.managers;


import io.github.jwdeveloper.ff.extension.gui.api.InventorySettings;
import io.github.jwdeveloper.ff.extension.gui.api.managers.buttons.ButtonManager;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;

import java.util.List;

public class ButtonManagerImpl implements ButtonManager {
    private ButtonUI[] buttons;
    private final InventorySettings settings;


    public ButtonManagerImpl(int height) {
        settings = new InventorySettings();
        settings.setHeight(height);
        buttons = new ButtonUI[settings.getHeight() * settings.getWidth()];
    }

    public ButtonManagerImpl(InventorySettings settings) {
        this.settings = settings;
        buttons = new ButtonUI[settings.getHeight() * settings.getWidth()];
    }

    @Override
    public ButtonUI[] getButtons() {
        return buttons;
    }

    @Override
    public void resize(int height, int width) {
        var lastButtonsSet = buttons;
        buttons = new ButtonUI[height * width];

        for (var lastButton : lastButtonsSet) {
            addButton(lastButton);
        }
    }

    @Override
    public void refresh() {
        if (!settings.hasHandle())
            return;

        for (var i = 0; i < buttons.length; i++) {
            if (buttons[i] == null) {
                settings.getHandle().setItem(i, null);
                continue;
            }
            refresh(buttons[i]);
        }
    }

    @Override
    public void refresh(List<ButtonUI> buttons) {
        if (!settings.hasHandle())
            return;

        for (var button : buttons) {
            refresh(button);
        }
    }

    @Override
    public void refresh(ButtonUI button) {
        if (button == null) {
            return;
        }
        var index = calculateButtonSlotIndex(button);
        if (index == -1)
            return;

        if (settings.hasStyleRenderer()) {
            settings.getStyleRenderer().render(button, button.getStyleRendererOptions());
        }

        settings.getHandle().setItem(index, button.isActive() ? button.getItemStack() : null);
    }


    public ButtonUI getButton(int height, int width) {
        var position = calculateButtonSlotIndex(height, width);
        return getButton(position);
    }

    public ButtonUI getButton(int index) {
        var position = index >= buttons.length ? buttons.length - 1 : index;
        return buttons[position];
    }

    @Override
    public ButtonUI getButton(String tag) {
        for (var button : buttons) {
            if (!button.hasTag()) {
                continue;
            }
            if (button.getTag().equals(tag)) {
                return button;
            }
        }
        return null;
    }

    public void addButton(ButtonUI button) {
        if (button == null) {
            return;
        }

        var slotIndex = calculateButtonSlotIndex(button);
        addButton(button, slotIndex);
    }

    public void addButtons(List<ButtonUI> buttons) {
        for (var button : buttons) {
            addButton(button);
        }
    }

    public void addButtons(ButtonUI[] buttons) {
        for (var button : buttons) {
            addButton(button);
        }
    }

    public void addButton(ButtonUI button, int slotIndex) {
        if (slotIndex <= settings.getSlots()) {
            buttons[slotIndex] = button;
        }
    }

    @Override
    public boolean hasButton(int height, int width) {
        return hasButton(calculateButtonSlotIndex(height, width));
    }

    @Override
    public boolean hasButton(int index) {
        return buttons[index] != null;
    }

    @Override
    public void removeButton(int index) {
        if (!hasButton(index)) {
            return;
        }
        buttons[index] = null;
    }

    @Override
    public void removeButton(ButtonUI buttonUI) {
        if (buttonUI == null) {
            return;
        }

        var index = calculateButtonSlotIndex(buttonUI);
        removeButton(index);
    }


    private int calculateButtonSlotIndex(ButtonUI button) {
        if (button.getHeight() > settings.getHeight() - 1) {
            button.setPosition(settings.getHeight() - 1, button.getWidth());
        }
        return calculateButtonSlotIndex(button.getHeight(), button.getWidth());
    }

    private int calculateButtonSlotIndex(int height, int width) {
        return height * InventorySettings.INVENTORY_WIDTH + width % InventorySettings.INVENTORY_WIDTH;
    }
}

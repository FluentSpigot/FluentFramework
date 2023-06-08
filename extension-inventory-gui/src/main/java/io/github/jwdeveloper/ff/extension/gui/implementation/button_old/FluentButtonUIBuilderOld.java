package io.github.jwdeveloper.ff.extension.gui.implementation.button_old;


import io.github.jwdeveloper.ff.extension.gui.api.references.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.observer_button.ButtonObserverUI;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.observer_button.observers.ButtonNotifier;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.observer_button.observers.ButtonObserverBuilder;
import io.github.jwdeveloper.ff.extension.gui.implementation.styles.DefaultStyleRendererOld;

import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.observer_button.ButtonObserverUIBuilder;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.observer_button.observers.ButtonObserver;
import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import org.bukkit.Color;
import org.bukkit.Material;
import io.github.jwdeveloper.ff.extension.gui.api.events.ClickGuiEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;


public class FluentButtonUIBuilderOld {
  //  private final StyleRendererOptionsBuilder descriptionBuilder;
    private final ButtonObserverUIBuilder buttonBuilder;
    private ButtonRef buttonUIRef;

    public FluentButtonUIBuilderOld() {
       // descriptionBuilder = new StyleRendererOptionsBuilder();
        buttonBuilder = new ButtonObserverUIBuilder();
        buttonUIRef = new ButtonRef();
    }

    public <T> FluentButtonUIBuilderOld setObserver(Observer<T> observer, ButtonNotifier<T> buttonNotifier) {
        buttonBuilder.addObserver(observer, buttonNotifier);
        return this;
    }

    public <T> FluentButtonUIBuilderOld setObserver(Supplier<Observer<T>> observer, ButtonNotifier<T> buttonNotifier) {
        buttonBuilder.addObserver(observer, buttonNotifier);
        return this;
    }

    public <T> FluentButtonUIBuilderOld setObserver(Consumer<ButtonObserverBuilder<T>> consumer) {
        var observer = new ButtonObserverBuilder<T>();
        consumer.accept(observer);
        buttonBuilder.addObserver(observer);
        return this;
    }

    public <T> FluentButtonUIBuilderOld setObserver(ButtonObserver<T> buttonObserver) {
        buttonBuilder.addObserver(buttonObserver);
        return this;
    }

/*
    public FluentButtonUIBuilderOld setDescription(Consumer<StyleRendererOptionsBuilder> consumer) {
        consumer.accept(descriptionBuilder);
        return this;
    }
*/
    public FluentButtonUIBuilderOld setLocation(int height, int width) {
        buttonBuilder.setLocation(height, width);
        return this;
    }

    public FluentButtonUIBuilderOld setMaterial(Material material) {
        buttonBuilder.setMaterial(material);
        return this;
    }

    public FluentButtonUIBuilderOld setMaterial(Material material, int customMaterialId) {
        buttonBuilder.setCustomMaterial(material, customMaterialId);
        return this;
    }

    public FluentButtonUIBuilderOld setMaterial(Material material, int customMaterialId, Color color)
    {
        buttonBuilder.setCustomMaterial(material, customMaterialId, color);
        return this;
    }

    public FluentButtonUIBuilderOld setPermissions(String... permissions) {
        buttonBuilder.setPermissions(permissions);
        return this;
    }
    public FluentButtonUIBuilderOld setOnRefresh(Consumer<ClickGuiEvent> event) {

        return this;
    }
    public FluentButtonUIBuilderOld setOnLeftClick(Consumer<ClickGuiEvent> event) {

        return this;
    }

    public FluentButtonUIBuilderOld setOnLeftClick(ButtonUIEventOLD event) {
        buttonBuilder.setOnClick(event);
        return this;
    }

    public FluentButtonUIBuilderOld setOnRightClick(ButtonUIEventOLD event) {
        buttonBuilder.setOnRightClick(event);
        return this;
    }

    public FluentButtonUIBuilderOld setOnShiftClick(ButtonUIEventOLD event) {
        buttonBuilder.setOnShiftClick(event);
        return this;
    }

    public FluentButtonUIBuilderOld setHighlighted() {
        buttonBuilder.setHighlighted();
        return this;
    }

    public FluentButtonUIBuilderOld setReference(ButtonRef refrence)
    {
        this.buttonUIRef = refrence;
        return this;
    }

    public ButtonObserverUI build(DefaultStyleRendererOld renderer) {
      /*  var description = renderer.render(descriptionBuilder.build());
        buttonBuilder.setDescription(description);
        buttonBuilder.setTitle(" ");
        var button = buttonBuilder.build();
        buttonUIRef.set(null);
        return button;*/
        return null;
    }
}

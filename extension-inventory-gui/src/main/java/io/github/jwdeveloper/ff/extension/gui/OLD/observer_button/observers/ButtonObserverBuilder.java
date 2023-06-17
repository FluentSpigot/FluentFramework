package io.github.jwdeveloper.ff.extension.gui.OLD.observer_button.observers;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.extension.gui.OLD.ButtonUIOld;

import java.util.function.Consumer;

public class ButtonObserverBuilder<T> {
    private Observer<T> observable;
    private ButtonUIOld buttonUI;
    private Consumer<ButtonObserverEvent<T>> onClickEvent = (a) -> {
    };
    private Consumer<ButtonObserverEvent<T>> onRightClick = (a) -> {
    };

    private Consumer<ButtonObserverEvent<T>> onChangeEvent = (a) -> {
    };

    public ButtonObserverBuilder<T> withObserver(Observer<T> observable) {
        this.observable = observable;
        return this;
    }

    public ButtonObserverBuilder<T> withButton(ButtonUIOld buttonUI) {
        this.buttonUI = buttonUI;
        return this;
    }

    public ButtonObserverBuilder<T> onClick(Consumer<ButtonObserverEvent<T>> event) {
        this.onClickEvent = event;
        return this;
    }

    public ButtonObserverBuilder<T> onRightClick(Consumer<ButtonObserverEvent<T>> event) {
        this.onRightClick = event;
        return this;
    }

    public ButtonObserverBuilder<T> onValueChange(Consumer<ButtonObserverEvent<T>> event) {
        this.onChangeEvent = event;
        return this;
    }

    public ButtonObserver<T> build() {
        var result = new ButtonObserver<>(observable, new ButtonNotifier() {
            @Override
            public void onLeftClick(ButtonObserverEvent event) {
                onClickEvent.accept(event);
            }

            @Override
            public void onRightClick(ButtonObserverEvent event) {
                onRightClick.accept(event);
            }

            @Override
            public void onValueChanged(ButtonObserverEvent event) {
                onChangeEvent.accept(event);
            }
        });
        result.setButtonUI(buttonUI);
        return result;
    }

}

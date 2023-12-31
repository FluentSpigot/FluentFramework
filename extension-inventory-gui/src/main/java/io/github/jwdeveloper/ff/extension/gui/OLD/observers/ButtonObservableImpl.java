package io.github.jwdeveloper.ff.extension.gui.OLD.observers;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.extension.gui.OLD.ButtonUIOld;
import io.github.jwdeveloper.ff.extension.gui.OLD.observer_button.observers.ButtonNotifier;
import io.github.jwdeveloper.ff.extension.gui.OLD.observer_button.observers.ButtonObservable;
import io.github.jwdeveloper.ff.extension.gui.OLD.observer_button.observers.ButtonObserverEvent;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

public class ButtonObservableImpl<T> implements ButtonObservable<T> {
    private final Supplier<Observer<T>> provider;
    private final ButtonNotifier<T> buttonNotifier;
    private final ButtonUIOld buttonUI;
    private Observer<T> currentObserver;


    public ButtonObservableImpl(Supplier<Observer<T>> observable,
                                ButtonNotifier<T> buttonNotifier,
                                ButtonUIOld buttonUI) {
        this.provider = observable;
        this.buttonNotifier = buttonNotifier;
        this.buttonUI = buttonUI;
    }

    @Override
    public ButtonUIOld getButtonUI() {
        return buttonUI;
    }


    public void leftClick(Player player) {
        if (!validateButton())
            return;
        buttonNotifier.onLeftClick(new ButtonObserverEvent<>(player, buttonUI, this, currentObserver.get()));
    }

    public void rightClick(Player player) {
        if (!validateButton())
            return;
        buttonNotifier.onRightClick(new ButtonObserverEvent<>(player, buttonUI, this, currentObserver.get()));
    }

    public void refresh() {
        if (buttonUI == null || !buttonUI.isActive())
            return;

        var observer = provider.get();
        if (observer == null) {
            return;
        }
        if (currentObserver != null && observer != currentObserver) {
            currentObserver.unsubscribe(this::onChangeEvent);

            currentObserver = observer;
            currentObserver.subscribe(this::onChangeEvent);
        }
        if (currentObserver == null) {
            currentObserver = observer;
            currentObserver.subscribe(this::onChangeEvent);
        }
        buttonNotifier.onValueChanged(new ButtonObserverEvent<>(null, buttonUI, this, currentObserver.get()));
    }


    public void onChangeEvent(T value) {
        if (!validateButton())
            return;
        buttonNotifier.onValueChanged(new ButtonObserverEvent(null, buttonUI, this, value));
    }


    public void setValue(T value) {
        if (currentObserver == null) {
            return;
        }
        currentObserver.set(value);
    }

    public T getValue() {
        if (currentObserver == null) {
            return null;
        }
        return currentObserver.get();
    }


    private boolean validateButton() {
        if (currentObserver == null) {
            FluentLogger.LOGGER.warning("OBSERVER NOT INITIALIZED YET", this.getButtonUI().getTitle(), this.toString());
            return false;
        }

        return buttonUI != null && buttonUI.isActive();
    }
}

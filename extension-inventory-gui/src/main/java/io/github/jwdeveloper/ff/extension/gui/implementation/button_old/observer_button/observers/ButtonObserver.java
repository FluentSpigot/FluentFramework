package io.github.jwdeveloper.ff.extension.gui.implementation.button_old.observer_button.observers;

import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.ButtonUIOld;
import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import org.bukkit.entity.Player;

public class ButtonObserver<T> implements ButtonObservable<T> {
    private final Observer<T> observable;
    private final ButtonNotifier buttonNotifier;
    public ButtonUIOld buttonUI;

    public ButtonObserver(Observer<T> observable, ButtonNotifier buttonNotifier)
    {
        this.observable = observable;
        this.buttonNotifier =buttonNotifier;
        this.observable.subscribe(value ->
        {
            if(!validateButton())
                return;
            buttonNotifier.onValueChanged(new ButtonObserverEvent(null,buttonUI,this,value));
        });
    }
    public static <T> ButtonObserverBuilder<T> builder()
    {
        return new ButtonObserverBuilder<T>();
    }

    @Override
    public ButtonUIOld getButtonUI() {
        return buttonUI;
    }

    public void setButtonUI(ButtonUIOld buttonUI)
    {
        this.buttonUI = buttonUI;
    }

    public void leftClick(Player player)
    {
        if(!validateButton())
            return;
        buttonNotifier.onLeftClick(new ButtonObserverEvent(player, buttonUI,this, observable.get()));
    }

    public void rightClick(Player player)
    {
        if(!validateButton())
            return;
        buttonNotifier.onRightClick(new ButtonObserverEvent(player, buttonUI,this, observable.get()));
    }

    public void refresh()
    {
        if(!validateButton())
            return;
        buttonNotifier.onValueChanged(new ButtonObserverEvent(null, buttonUI,this, observable.get()));
    }

    public void setValue(T value)
    {
        observable.set(value);
    }

    public T getValue()
    {
        return observable.get();
    }

    private boolean validateButton()
    {
        return buttonUI != null && buttonUI.isActive();
    }
}

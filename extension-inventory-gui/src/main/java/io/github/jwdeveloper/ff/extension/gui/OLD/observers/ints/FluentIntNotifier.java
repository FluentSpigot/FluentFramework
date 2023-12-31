package io.github.jwdeveloper.ff.extension.gui.OLD.observers.ints;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.OLD.observers.ButtonNotifierBase;
import io.github.jwdeveloper.ff.extension.gui.OLD.observer_button.observers.ButtonObserverEvent;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;

public class FluentIntNotifier extends ButtonNotifierBase<Integer> {
    private final FluentTranslator translator;
    protected final IntNotifierOptions options;

    public FluentIntNotifier(FluentTranslator translator, IntNotifierOptions notifierOptions) {
        super(notifierOptions);
        this.translator = translator;
        this.options = notifierOptions;
    }

    @Override
    protected void onInitialize(ButtonObserverEvent<Integer> event) {
        if (StringUtils.isNullOrEmpty(options.getPrefix())) {
            options.setPrefix(translator.get("gui.base.value"));
        }
    }

    @Override
    protected void onUpdate(ButtonObserverEvent<Integer> event) {
      //  event.getButton().updateDescription(getDescriptionIndex()        , new MessageBuilder().field(options.getPrefix(), event.getValue()).toString());

    }


    @Override
    public void onRightClick(ButtonObserverEvent<Integer> event) {
        if (event.getValue() - options.getYield() < options.getMinimum()) {
            event.getObserver().setValue(options.getMaximum());
            return;
        }
        event.getObserver().setValue(event.getValue() - options.getYield());
    }

    @Override
    public void onLeftClick(ButtonObserverEvent<Integer> event) {
        if (event.getValue() + options.getYield() > options.getMaximum()) {
            event.getObserver().setValue(options.getMinimum());
            return;
        }
        event.getObserver().setValue(event.getValue() + options.getYield());
    }
}

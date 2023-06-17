package io.github.jwdeveloper.ff.extension.gui.OLD.observers.list.checkbox;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckBox {
    private String name = StringUtils.EMPTY;
    private Observer<Boolean> observer = new ObserverBag<Boolean>(false).getObserver();
    private String permission = StringUtils.EMPTY;

    public CheckBox() {

    }
}

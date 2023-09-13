package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list_check_box;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

@Data

public class ListCheckBoxModel
{
    private String name;

    private Observer<Boolean> observer;


    public ListCheckBoxModel(String name, Observer<Boolean> observer) {
        this.name = name;
        this.observer = observer;
    }

    public ListCheckBoxModel() {

    }
}

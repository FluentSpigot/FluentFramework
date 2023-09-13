package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list_check_box;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListOptions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ContentListCheckBoxOptions extends ContentListOptions<ListCheckBoxModel>
{


    List<ListCheckBoxModel> listCheckBoxModels = new ArrayList<>();

    String enabled;

    String disabled;

    String prefix;

    public void addCheckBox(String name, Observer<Boolean> observer)
    {
        listCheckBoxModels.add(new ListCheckBoxModel(name,observer));
    }

    public void addCheckBox(Consumer<ListCheckBoxModel> consumer)
    {
        var model = new ListCheckBoxModel();
        consumer.accept(model);
        listCheckBoxModels.add(model);
    }
}

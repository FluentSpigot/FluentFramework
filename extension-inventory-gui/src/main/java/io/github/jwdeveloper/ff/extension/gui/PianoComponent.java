package io.github.jwdeveloper.ff.extension.gui;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;

@Injection(lifeTime = LifeTime.PLAYER_SCOPE)
public class PianoComponent implements InventoryComponent
{

    @Override
    public void onCreate(InventoryDecorator decorator) {


    }

}

package io.github.jw.spigot.ff.example;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.jw.spigot.ff.example.aib.ExampleBlockItem;
import io.github.jw.spigot.ff.example.drill.BlockCanon;
import io.github.jw.spigot.ff.example.drill.BlockPlaceTest;
import io.github.jw.spigot.ff.example.drill.ChopWood;
import io.github.jw.spigot.ff.example.drill.DrillItem;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;


public class Extension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.container().registerSingleton(ProtocolManager.class, e -> ProtocolLibrary.getProtocolManager());


    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        new DrillItem().register(fluentAPI);
        new ChopWood().register(fluentAPI);
        new BlockCanon().register(fluentAPI);
        new BlockPlaceTest().register(fluentAPI);
        new ExampleBlockItem().register();
    }


}

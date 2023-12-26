package io.github.jwdeveloper.ff.advancements.implementation;

import com.fren_gor.ultimateAdvancementAPI.AdvancementMain;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.Material;

public class AdvancementExtension implements FluentApiExtension {
    UltimateAdvancementAPI api;
    private AdvancementMain main;

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
         main = new AdvancementMain(builder.plugin());

        try {
            var clazz = UltimateAdvancementAPI.class;

            for(var filed : clazz.getDeclaredFields())
            {
                FluentLogger.LOGGER.info("Field!",filed.getName());
            }
            var filed = clazz.getDeclaredField("main");
            filed.setAccessible(true);
            filed.set(null,main);
            FluentLogger.LOGGER.info("Field!",filed.get(null));
        }
        catch (Exception e)
        {
            builder.logger().error("We have error",e);
        }
        api = UltimateAdvancementAPI.getInstance(builder.plugin());
        main.load();
        main.enableInMemory();
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var defaultTab = api.createAdvancementTab("default");
        var oak_sapling0 = new RootAdvancement(defaultTab,
                "oak_sapling0",
                new AdvancementDisplay(Material.OAK_SAPLING, "oak_sapling0", AdvancementFrameType.TASK, true, true, 0f, 0f),
                "textures/block/red_terracotta.png", 1);

        defaultTab.registerAdvancements(oak_sapling0);
        defaultTab.automaticallyShowToPlayers();
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
        main.disable();
    }
}

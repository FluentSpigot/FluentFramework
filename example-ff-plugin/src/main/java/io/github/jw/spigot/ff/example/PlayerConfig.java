package io.github.jw.spigot.ff.example;


import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player.PlayerScope;
import org.bukkit.entity.Player;

@Injection(lifeTime = LifeTime.TRANSIENT)
@PlayerScope
public class PlayerConfig {
    private String name;
    private String lastName;

    private final ExampleConfig exampleConfig;

    public PlayerConfig(Player player, ExampleConfig exampleConfig) {
        this.name = player.getName();
        this.lastName = player.getUniqueId().toString();
        this.exampleConfig = exampleConfig;
        FluentLogger.LOGGER.info(exampleConfig.toString());
    }
}

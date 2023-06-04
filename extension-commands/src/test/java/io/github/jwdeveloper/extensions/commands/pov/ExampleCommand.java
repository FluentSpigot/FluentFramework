package io.github.jwdeveloper.extensions.commands.pov;

import io.github.jwdeveloper.ff.extension.commands.api.annotations.Argument;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.AccessType;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentType;
import org.bukkit.entity.Player;

@Argument(name = "player-name")
@Argument(name = "player-age",argumentType = ArgumentType.INT)
@Command(name = "name",  description = "test")
public class ExampleCommand
{
    @Command
    public void onInvoke()
    {

    }

    @Command(name = "join",
             description = "test",
             access = AccessType.PLAYER)
    public void onJoin(Player player, String dupa)
    {
        var i =0;
    }
}

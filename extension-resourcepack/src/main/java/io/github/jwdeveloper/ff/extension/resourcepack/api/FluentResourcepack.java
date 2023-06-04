package io.github.jwdeveloper.ff.extension.resourcepack.api;

import org.bukkit.entity.Player;

public interface FluentResourcepack {
    void downloadResourcepack(Player player);

    void sendResourcepackInfo(Player player);
}

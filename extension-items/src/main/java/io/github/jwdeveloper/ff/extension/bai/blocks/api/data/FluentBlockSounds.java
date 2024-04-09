package io.github.jwdeveloper.ff.extension.bai.blocks.api.data;

import lombok.Data;
import org.bukkit.Sound;

@Data
public class FluentBlockSounds {

    private Sound place = Sound.BLOCK_STONE_PLACE;

    private Sound damage = Sound.BLOCK_STONE_HIT;

    private Sound destroy = Sound.BLOCK_STONE_BREAK;
}

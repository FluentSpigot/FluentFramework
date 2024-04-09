package io.github.jwdeveloper.ff.extension.bai.blocks.api.builder;

import org.bukkit.Sound;

public interface BlockSoundsBuilder
{
    BlockSoundsBuilder place(Sound sound);

    BlockSoundsBuilder damage(Sound sound);

    BlockSoundsBuilder destroy(Sound sound);
}

package io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockSoundsBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockSounds;
import org.bukkit.Sound;

public class SoundsBuilder implements BlockSoundsBuilder {

    private final FluentBlockSounds sounds = new FluentBlockSounds();

    @Override
    public BlockSoundsBuilder place(Sound sound) {
        sounds.setPlace(sound);
        return this;
    }

    @Override
    public BlockSoundsBuilder damage(Sound sound) {
        sounds.setDamage(sound);
        return this;
    }

    @Override
    public BlockSoundsBuilder destroy(Sound sound) {
        sounds.setDestroy(sound);
        return this;
    }

    public FluentBlockSounds build() {
        return sounds;
    }
}

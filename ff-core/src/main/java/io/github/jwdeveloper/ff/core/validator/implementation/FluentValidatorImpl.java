package io.github.jwdeveloper.ff.core.validator.implementation;

import io.github.jwdeveloper.ff.core.validator.api.FluentValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.block.BlockValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.entity.EntityValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.item.ItemMetaValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.item.ItemStackValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.location.LocationValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.player.PlayerValidator;

public class FluentValidatorImpl implements FluentValidator {
    @Override
    public PlayerValidator player() {
        return new PlayerValidator(this);
    }

    @Override
    public EntityValidator entity() {
        return new EntityValidator(this);
    }

    @Override
    public BlockValidator block() {
        return new BlockValidator();
    }

    @Override
    public LocationValidator location() {
        return new LocationValidator();
    }

    @Override
    public ItemStackValidator itemStack() {
        return new ItemStackValidator(this);
    }

    @Override
    public ItemMetaValidator itemMeta() {
        return new ItemMetaValidator();
    }

}

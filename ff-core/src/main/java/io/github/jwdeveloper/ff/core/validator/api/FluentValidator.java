package io.github.jwdeveloper.ff.core.validator.api;

import io.github.jwdeveloper.ff.core.validator.implementation.block.BlockValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.entity.EntityValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.item.ItemMetaValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.item.ItemStackValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.location.LocationValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.player.PlayerValidator;

public interface FluentValidator {
    PlayerValidator player();

    EntityValidator entity();

    BlockValidator block();

    LocationValidator location();

    ItemStackValidator itemStack();
    ItemMetaValidator itemMeta();
}

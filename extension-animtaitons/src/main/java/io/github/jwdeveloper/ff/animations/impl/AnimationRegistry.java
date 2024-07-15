package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.core.common.registry.RegistryBase;

import java.util.List;
import java.util.Optional;

public class AnimationRegistry extends RegistryBase<FluentAnimation> {

    @Override
    public List<FluentAnimation> findByTag(String tag) {
        return super.findByTag(tag);
    }

    @Override
    public Optional<FluentAnimation> findByName(String uniqueName) {
        return items.stream().filter(e -> uniqueName.equals(e.getName())).findFirst();
    }
}

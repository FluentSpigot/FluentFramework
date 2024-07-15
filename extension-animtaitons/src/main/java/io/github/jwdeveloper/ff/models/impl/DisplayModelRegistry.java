package io.github.jwdeveloper.ff.models.impl;

import io.github.jwdeveloper.ff.core.common.registry.Registry;
import io.github.jwdeveloper.ff.core.common.registry.RegistryBase;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModel;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModelRegistry;

import java.util.List;
import java.util.Optional;

public class DisplayModelRegistry extends RegistryBase<FluentDisplayModel> implements FluentDisplayModelRegistry {


    @Override
    public Optional<FluentDisplayModel> findByName(String uniqueName)
    {
        return items.stream().filter(e -> e.getName().equals(uniqueName)).findFirst();
    }
}

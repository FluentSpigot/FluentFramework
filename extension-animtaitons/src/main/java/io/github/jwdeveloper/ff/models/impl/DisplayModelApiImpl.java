package io.github.jwdeveloper.ff.models.impl;

import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.models.api.DisplayModelApi;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModel;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModelRegistry;
import io.github.jwdeveloper.ff.models.impl.parsers.BlockBenchModelParser;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;

import java.util.List;

public class DisplayModelApiImpl implements DisplayModelApi {


    private final FluentDisplayModelRegistry registry;

    public DisplayModelApiImpl(FluentDisplayModelRegistry registry) {
        this.registry = registry;
    }

    @Override
    public ActionResult<FluentDisplayModel> loadFromBlockBench(String json) {
        return FluentApi.container().findInjection(BlockBenchModelParser.class).parse(json);
    }

    @Override
    public List<FluentDisplayModel> findModels() {
        return registry.findAll();
    }

    @Override
    public ActionResult<FluentDisplayModel> findModel(String name) {
        return ActionResult.fromOptional(registry.findByName(name));
    }
}

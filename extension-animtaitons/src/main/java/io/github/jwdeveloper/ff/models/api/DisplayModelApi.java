package io.github.jwdeveloper.ff.models.api;

import io.github.jwdeveloper.ff.core.common.ActionResult;

import java.util.List;

public interface DisplayModelApi
{
    ActionResult<FluentDisplayModel> loadFromBlockBench(String json);
    List<FluentDisplayModel> findModels();
    ActionResult<FluentDisplayModel> findModel(String name);
}

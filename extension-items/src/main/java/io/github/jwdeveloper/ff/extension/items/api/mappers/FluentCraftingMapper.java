package io.github.jwdeveloper.ff.extension.items.api.mappers;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCrafting;

import java.util.List;

public interface FluentCraftingMapper
{
      ActionResult<FluentCrafting> map(List<String> data, FluentItem target);
}

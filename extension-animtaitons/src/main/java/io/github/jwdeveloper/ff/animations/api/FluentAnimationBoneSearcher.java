package io.github.jwdeveloper.ff.animations.api;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import org.bukkit.entity.Entity;

public interface FluentAnimationBoneSearcher
{
      ActionResult<Entity> findBoneEntity(String boneName, Entity... parent);
}

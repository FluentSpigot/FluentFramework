package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.FluentAnimationBoneSearcher;
import io.github.jwdeveloper.ff.core.common.ActionResult;
import org.bukkit.entity.Entity;

public class AnimationBoneSearcher implements FluentAnimationBoneSearcher {

    @Override
    public ActionResult<Entity> findBoneEntity(String boneName, Entity... parent) {

        for (var entity : parent) {
            var meta = entity.getMetadata("bone-name");
            if (!meta.isEmpty()) {
                var value = meta.get(0).asString();
                if (value.equals(boneName)) {
                    return ActionResult.success(entity);
                }
            }

            for (var passager : entity.getPassengers()) {
                var result = findBoneEntity(boneName, passager);
                if (result.isSuccess()) {
                    entity.removePassenger(passager);
                    return result;
                }
            }
        }
        return ActionResult.failed("bone not found!");
    }


}

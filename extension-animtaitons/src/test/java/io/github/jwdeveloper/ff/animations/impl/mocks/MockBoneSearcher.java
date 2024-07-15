package io.github.jwdeveloper.ff.animations.impl.mocks;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationBoneSearcher;
import io.github.jwdeveloper.ff.core.common.ActionResult;
import org.bukkit.entity.Entity;

public class MockBoneSearcher implements FluentAnimationBoneSearcher
{



    @Override
    public ActionResult<Entity> findBoneEntity(String boneName, Entity... parent) {
        var entity = MockBukkit.getMock().addPlayer();
        return ActionResult.success(entity);
    }
}

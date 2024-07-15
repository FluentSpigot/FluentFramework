package io.github.jwdeveloper.ff.animations.impl.player;

import io.github.jwdeveloper.ff.animations.api.nodes.TimelineContext;
import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import lombok.Data;
import org.bukkit.entity.Entity;

@Data
public class TimeLineContextImpl implements TimelineContext
{

    private Entity entity;

    private TransformationUtility.TransformationBuilder transformation;

    @Override
    public Entity getEntity() {
        return entity;
    }
}

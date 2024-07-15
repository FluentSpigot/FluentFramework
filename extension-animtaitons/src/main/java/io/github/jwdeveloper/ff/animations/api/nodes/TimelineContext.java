package io.github.jwdeveloper.ff.animations.api.nodes;

import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import org.bukkit.entity.Entity;

public interface TimelineContext
{
     Entity getEntity();

     TransformationUtility.TransformationBuilder getTransformation();
}

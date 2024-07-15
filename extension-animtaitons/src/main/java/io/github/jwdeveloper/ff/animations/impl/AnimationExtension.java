package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationBoneSearcher;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationBuilder;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationPlayer;
import io.github.jwdeveloper.ff.animations.impl.data.custom.NodeDataMapper;
import io.github.jwdeveloper.ff.animations.impl.parsers.AnimationParser;
import io.github.jwdeveloper.ff.animations.impl.parsers.BlockBenchParser;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class AnimationExtension implements FluentApiExtension {

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        var container = builder.container();
        container.registerTransient(FluentAnimationBoneSearcher.class, AnimationBoneSearcher.class);
        container.registerTransient(FluentAnimationPlayer.class, AnimationPlayer.class);
        container.registerTransient(FluentAnimationBuilder.class, AnimationBuilder.class);
        container.registerSingleton(AnimationApi.class, AnimationApiImpl.class);
        container.registerSingleton(AnimationRegistry.class);
        container.registerTransient(AnimationParser.class);
        container.registerTransient(BlockBenchParser.class);
        container.registerSingleton(NodeDataMapper.class);
    }
}

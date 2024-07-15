package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationBoneSearcher;
import io.github.jwdeveloper.ff.animations.impl.mocks.MockBoneSearcher;
import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.core.common.java.JavaUtils;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import org.bukkit.Particle;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ExtensionTest extends FluentApiTest {
    @Override
    public void onBuild(FluentApiSpigotBuilder fluentApiBuilder) {

        fluentApiBuilder.useExtension(new AnimationExtension());
        fluentApiBuilder.useExtension(builder ->
        {
            builder.container().registerTransient(FluentAnimationBoneSearcher.class, MockBoneSearcher.class);
        });
    }


    @Test
    public void CheckBasicAnimation() throws IOException {
        var player = getPlayer();
        var api = getContainer().findInjection(AnimationApi.class);
        var anim1 = api.createAnimation()
                .withName("example animation")
                .thenScale(1, 1, 1)
                .thenTransform(2, 3, 3)
                .thenWait(20)
                .thenParticle(Particle.HEART)
                .thenParticle(Particle.CLOUD)
                .thenWait(30)
                .thenRotate(30, 3, 3, 3)
                .thenWait(20)
                .build();
        var timeLine = api.playAnimation(anim1, player);
        JavaUtils.waitUntil(timeLine::isPlaying);
    }

    @Test
    public void CheckBlockBench()  {
        var player = getPlayer();
        var content = loadResourceAsStream(getClass(), "example-blockbench.json");
        var api = getContainer().findInjection(AnimationApi.class);
        var animations = api.loadFromBlockBench(content);
        var animation = animations.get(0);
        var animator = api.playAnimation(animation, player);

        JavaUtils.waitUntil(animator::isPlaying);
    }
}

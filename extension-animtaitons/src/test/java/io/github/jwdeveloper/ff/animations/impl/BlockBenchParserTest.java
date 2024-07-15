package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlockBenchParserTest extends FluentApiTest {
    @Override
    public void onBuild(FluentApiSpigotBuilder fluentApiBuilder) {
        fluentApiBuilder.useExtension(new AnimationExtension());
    }

    @Test
    public void ShouldParseBlockBenchAnimation() {
        var content = loadResourceAsStream(getClass(), "example-blockbench.json");
        var api = getContainer().findInjection(AnimationApi.class);
        var animations = api.loadFromBlockBench(content);

        Assertions.assertEquals(1, animations.size());
        var animation = animations.get(0);

        Assertions.assertEquals("example", animation.getName());
        Assertions.assertEquals(1875, animation.getDurationMs());
        Assertions.assertEquals(2, animation.getBones().size());
        Assertions.assertTrue(animation.hasBone("head"));
        Assertions.assertTrue(animation.hasBone("neck"));
    }
}

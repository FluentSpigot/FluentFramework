package io.github.jwdeveloper.ff.models;

import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.animations.impl.AnimationExtension;
import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.models.api.DisplayModelApi;
import io.github.jwdeveloper.ff.models.impl.ModelExtension;
import io.github.jwdeveloper.ff.models.impl.parsers.BlockBenchModelParser;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelsParserTests extends FluentApiTest {
    @Override
    public void onBuild(FluentApiSpigotBuilder fluentApiBuilder) {
        fluentApiBuilder.useExtension(new AnimationExtension());
        fluentApiBuilder.useExtension(new ModelExtension());
    }

    @Test
    public void ShouldParseBlockBenchAnimation() {
        var content = loadResourceAsStream(getClass(), "example-model.json");

        var api = getContainer().findInjection(DisplayModelApi.class);
        var result = api.loadFromBlockBench(content);

        Assertions.assertTrue(result.isSuccess());
    }
}
package io.github.jwdeveloper.ff;

import io.github.jwdeveloper.ff.animations.impl.AnimationExtension;
import io.github.jwdeveloper.ff.models.impl.ModelExtension;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class DisplayModelFrameworkExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.useExtension(new ModelExtension());
        builder.useExtension(new AnimationExtension());
    }
}

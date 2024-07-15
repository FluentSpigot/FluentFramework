package io.github.jwdeveloper.ff.models.api;

import io.github.jwdeveloper.ff.animations.api.FluentAnimation;

import java.util.List;

public interface FluentDisplayModelInstance {

    FluentDisplayModel getModel();

    void playAnimation(FluentAnimation animation);

    void remove();
}

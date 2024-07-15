package io.github.jwdeveloper.ff;

import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.models.api.DisplayModelApi;

public class DisplayModelFramework
{
    public static Class<?> ANIMATIONS_API = AnimationApi.class;
    public static Class<?> MODELS_API = DisplayModelApi.class;

    public static DisplayModelFrameworkExtension use()
    {
        return new DisplayModelFrameworkExtension();
    }


}

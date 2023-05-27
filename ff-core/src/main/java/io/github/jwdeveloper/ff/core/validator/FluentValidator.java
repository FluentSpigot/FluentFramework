package io.github.jwdeveloper.ff.core.validator;

import io.github.jwdeveloper.ff.core.validator.api.ValidatorFactory;
import io.github.jwdeveloper.ff.core.validator.implementation.ValidatorFactoryImpl;

public class FluentValidator
{
    public static ValidatorFactory validators()
    {
        return new ValidatorFactoryImpl();
    }
}

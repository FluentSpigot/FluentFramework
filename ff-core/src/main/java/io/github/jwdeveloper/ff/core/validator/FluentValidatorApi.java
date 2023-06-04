package io.github.jwdeveloper.ff.core.validator;

import io.github.jwdeveloper.ff.core.validator.api.FluentValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.FluentValidatorImpl;

public class FluentValidatorApi
{
    public static FluentValidator validators()
    {
        return new FluentValidatorImpl();
    }
}

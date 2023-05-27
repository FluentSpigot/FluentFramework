package io.github.jwdeveloper.ff.core.validator.api;

import io.github.jwdeveloper.ff.core.common.ActionResult;

public interface ValidationRule<T>
{
    ActionResult<T> validate(T target);
}

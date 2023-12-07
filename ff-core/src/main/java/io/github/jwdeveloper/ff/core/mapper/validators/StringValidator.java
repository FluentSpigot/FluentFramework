package io.github.jwdeveloper.ff.core.mapper.validators;

import io.github.jwdeveloper.ff.core.common.ActionResult;

public class StringValidator implements ConfigValidator
{

    public StringValidator isEmpty()
    {
        return this;
    }

    public StringValidator isNotEmpty()
    {
        return this;
    }

    public StringValidator hasPatter(String pattern)
    {
        return this;
    }


    @Override
    public ActionResult<ValidationResult> validate() {
        return null;
    }
}

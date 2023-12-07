package io.github.jwdeveloper.ff.core.mapper.validators;

import io.github.jwdeveloper.ff.core.common.ActionResult;

import java.util.List;

public interface ConfigValidator {
    ActionResult<ValidationResult> validate();
}

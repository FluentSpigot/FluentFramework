package io.github.jwdeveloper.ff.core.validator.api;

import java.util.function.Function;

public interface Validator<ValidationTarget, ValidatorImpl extends Validator> extends ValidationRule<ValidationTarget>
{
    ValidatorImpl mustBe(ValidationTarget target);
    ValidatorImpl mustComplyRule(ValidationRule<ValidationTarget> rule);
    ValidatorImpl mustComplyRule(Function<ValidationTarget, Boolean> rule, String errorMessage);
}

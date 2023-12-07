package io.github.jwdeveloper.ff.core.validator.implementation;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.validator.api.ValidationRule;
import io.github.jwdeveloper.ff.core.validator.api.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class ValidatorBase<Model, SubClass extends ValidatorBase> implements Validator<Model, SubClass> {

    private final List<ValidationRule<Model>> validationRules;

    public ValidatorBase() {
        validationRules = new ArrayList<>();
    }

    @Override
    public SubClass mustBe(Model target) {
        return mustComplyRule(e ->
        {
            if (e == null && target == null) {
                return true;
            }
            if (e == null) {
                return false;
            }
            if (target == null) {
                return false;
            }
            return e.equals(target);
        }, "validated object is not equal to target");
    }

    @Override
    public SubClass mustComplyRule(ValidationRule<Model> rule) {
        validationRules.add(rule);
        return This();
    }

    @Override
    public SubClass mustComplyRule(Function<Model, Boolean> rule, String errorMessage) {
        ValidationRule<Model> validationRule = (target) ->
        {
            var result = rule.apply(target);
            return result ? ActionResult.success(target) : ActionResult.failed(target, errorMessage);
        };
        return mustComplyRule(validationRule);
    }


    @Override
    public ActionResult<Model> validate(Model target)
    {
        if(target == null)
        {
            return ActionResult.failed("target is null");
        }

        for (var rule : validationRules) {
            var result = rule.validate(target);
            if (result.isFailed()) {
                return result;
            }
        }
        return ActionResult.success(target);
    }


    protected SubClass This() {
        return (SubClass) this;
    }
}

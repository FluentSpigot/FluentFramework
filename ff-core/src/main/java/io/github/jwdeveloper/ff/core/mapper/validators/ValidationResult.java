package io.github.jwdeveloper.ff.core.mapper.validators;

import io.github.jwdeveloper.ff.core.common.ActionResult;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult extends ActionResult<List<String>> {


    public void addError(String error) {
        this.setSuccess(false);
        if (this.getContent() == null) {
            this.setContent(new ArrayList<>());
        }
        this.getContent().add(error);
    }
}

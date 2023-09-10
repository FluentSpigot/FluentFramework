package io.github.jwdeveloper.ff.extension.files.implementation.builder;

import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FluentFileBuilderResult {
    @Getter
    private final List<FluentFileModel> fluentFileModels = new ArrayList<>();
}

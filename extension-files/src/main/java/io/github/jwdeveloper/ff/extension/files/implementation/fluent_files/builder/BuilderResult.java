package io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.builder;

import io.github.jwdeveloper.ff.core.common.java.PairCollection;
import lombok.Getter;

public class BuilderResult {
    @Getter
    private PairCollection<Class<?>, Object> jsonFiles = new PairCollection<>();
}

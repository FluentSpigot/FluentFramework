package io.github.jwdeveloper.ff.extension.files.api;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import lombok.Data;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Data
public class FluentFileModel
{
    private Class<?> classType;
    private Object object;
    private String customPath;

    private FluentFileType type;

    private Consumer<String> onIfFileNotFound;

    private boolean allowAutomaticSaving = true;

    public boolean hasObject()
    {
        return object != null;
    }

    public boolean hasCustomPath()
    {
        return StringUtils.isNotNullOrEmpty(customPath);
    }

}

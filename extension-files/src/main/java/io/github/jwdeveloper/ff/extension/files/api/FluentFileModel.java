package io.github.jwdeveloper.ff.extension.files.api;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import lombok.Data;

@Data
public class FluentFileModel
{
    private Class<?> classType;
    private Object object;
    private String customPath;

    private FluentFileType type;

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

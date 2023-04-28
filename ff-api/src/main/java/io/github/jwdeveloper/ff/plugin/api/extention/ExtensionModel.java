package io.github.jwdeveloper.ff.api.api.extention;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtensionModel
{
    private FluentApiExtension extension;

    private ExtentionPiority priority;
}

package io.github.jwdeveloper.ff.extension.resourcepack.implementation;

import lombok.Data;

@Data
public class ResourcepackOptions
{
    private String name;

    private String defaultUrl;

    private boolean loadOnJoin;
}

package io.github.jwdeveloper.ff.plugin.implementation.extensions.resourcepack;

import lombok.Data;

@Data
public class ResourcepackOptions
{
    private String name;

    private String defaultUrl;

    private boolean loadOnJoin;
}
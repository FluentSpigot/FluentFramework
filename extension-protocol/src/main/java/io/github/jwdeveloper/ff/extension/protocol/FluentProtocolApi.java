package io.github.jwdeveloper.ff.extension.protocol;

import io.github.jwdeveloper.ff.extension.protocol.impl.FluentProtocolExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class FluentProtocolApi
{
    public static FluentApiExtension use() {
        return new FluentProtocolExtension();
    }
}

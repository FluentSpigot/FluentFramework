package io.github.jwdeveloper.ff.extension.files;

import io.github.jwdeveloper.ff.extension.files.implementation.FluentFilesExtention;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class FluentFilesApi
{
    public static FluentApiExtension use()
    {
         return new FluentFilesExtention();
    }
}

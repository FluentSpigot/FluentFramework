package io.github.jwdeveloper.ff.plugin.implementation.extensions.updater.api.info;

import lombok.Data;

@Data
public class UpdateInfo
{
       private String fileName;
       private String downloadUrl;
       private String version;
       private String description;
}

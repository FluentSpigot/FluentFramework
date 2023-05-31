package io.github.jwdeveloper.ff.extension.updater.api.info;

import lombok.Data;

@Data
public class UpdateInfoResponse
{
       private String fileName;
       private String downloadUrl;
       private String version;
       private String description;
}

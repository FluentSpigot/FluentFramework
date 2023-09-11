package io.github.jwdeveloper.ff.extension.updater.api.info;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import lombok.Data;

@Data
public class UpdateInfoResponse
{
       private String fileName= StringUtils.EMPTY;
       private String downloadUrl= StringUtils.EMPTY;
       private String version= StringUtils.EMPTY;
       private String description = StringUtils.EMPTY;
       public final static UpdateInfoResponse NOT_FOUND = new UpdateInfoResponse();
}

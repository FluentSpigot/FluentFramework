package io.github.jwdeveloper.ff.extension.updater.api;

import io.github.jwdeveloper.ff.extension.updater.api.info.UpdateInfoResponse;


import java.io.IOException;

public interface UpdateInfoProvider
{
     UpdateInfoResponse getUpdateInfo() throws IOException;
}

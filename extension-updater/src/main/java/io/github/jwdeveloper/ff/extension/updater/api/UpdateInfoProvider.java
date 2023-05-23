package io.github.jwdeveloper.ff.extension.updater.api;

import io.github.jwdeveloper.ff.extension.updater.api.info.UpdateInfo;


import java.io.IOException;

public interface UpdateInfoProvider
{
     UpdateInfo getUpdateInfo() throws IOException;
}

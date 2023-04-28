package io.github.jwdeveloper.ff.api.implementation.extensions.updater.api;

import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.info.UpdateInfo;

import java.io.IOException;

public interface UpdateInfoProvider
{
     UpdateInfo getUpdateInfo() throws IOException;
}

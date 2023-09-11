package io.github.jwdeveloper.ff.tools;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.plugin.tests.FluentPluginTest;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.file.Path;



public abstract class FluentTaskAction extends FluentPluginTest
{

    public String getResourcepackItemPath(String resourcePackName, String ... params)
    {
        var basePath= new String[5];
        basePath[0] = resourcePackName;
        basePath[1] = "assets";
        basePath[2] = "minecraft";
        basePath[3] = "models";
        basePath[4] = "item";

        return Path.of(getResourcepackPath(), ArrayUtils.addAll(basePath, params)).toString();
    }

    public String getResourcepackPath(String ... params)
    {
        return Path.of(getResourcepackPath(), params).toString();
    }
    public String getResourcepackPath()
    {
        return Path.of( System.getenv("APPDATA"), ".minecraft","resourcepacks").toString();
    }

    public String getProjectPath()
    {
        return FileUtility.getProjectPath();
    }

    public String getCodePath()
    {
        return Path.of(getProjectPath(), "src","main","java").toString();
    }

    public String getCodePath(String ... params)
    {
        return Path.of(getCodePath(), params).toString();
    }

    public String getCodeTestPath()
    {
        return Path.of(getProjectPath(), "src","test","java").toString();
    }

    public String getCodeTestPath(String ... params)
    {
        return Path.of(getCodeTestPath(), params).toString();
    }

    public String getResourcePath()
    {
        return Path.of(getProjectPath(), "src","main","resources").toString();
    }

    public String getResourcePath(String ... params)
    {
        return Path.of(getResourcePath(), params).toString();
    }

    public String getTestResourcePath()
    {
        return Path.of(getProjectPath(), "src","test","resources").toString();
    }

    public String getTestResourcePath(String ... params)
    {
        return Path.of( getTestResourcePath(), params).toString();
    }


}

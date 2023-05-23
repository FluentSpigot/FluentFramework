package io.github.jwdeveloper.ff.tools;
import java.nio.file.Path;


public class TaskBase
{
    public void getCurrentPath()
    {

    }

    public String getProjectPath()
    {
        return System.getProperty("user.dir");
    }

    public String getCodePath()
    {
        return Path.of(getProjectPath(), "src","main","java").toString();
    }

    public String getCodePath(String ... params)
    {
        return Path.of(getCodePath(), params).toString();
    }

    public String getResourcePath()
    {
        return Path.of(getProjectPath(), "src","main","resources").toString();
    }

    public String getResourcePath(String ... params)
    {
        return Path.of(getResourcePath(), params).toString();
    }
}

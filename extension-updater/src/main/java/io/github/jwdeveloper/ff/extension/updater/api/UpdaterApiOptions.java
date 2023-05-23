package io.github.jwdeveloper.ff.extension.updater.api;
import io.github.jwdeveloper.ff.extension.updater.api.options.UpdaterOptions;
import io.github.jwdeveloper.ff.extension.updater.api.options.builder.GithubOptionsBuilder;

public class UpdaterApiOptions
{
    private GithubOptionsBuilder githubOptionsBuilder;

    public UpdaterApiOptions()
    {
        githubOptionsBuilder = new GithubOptionsBuilder();
    }
    public GithubOptionsBuilder useGithub()
    {
        return githubOptionsBuilder;
    }

    public UpdaterOptions getOptions()
    {
        return githubOptionsBuilder.build();
    }
}

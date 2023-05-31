package io.github.jwdeveloper.ff.extension.updater.api.options.builder;

import io.github.jwdeveloper.ff.extension.updater.api.options.GithubUpdaterOptions;


public class GithubOptionsBuilder
{
    private final GithubUpdaterOptions options;

    public GithubOptionsBuilder()
    {
        options = new GithubUpdaterOptions();
    }

    public GithubOptionsBuilder setGithubUserName(String userName)
    {
        options.setGithubUserName(userName);
        return this;
    }

    public GithubOptionsBuilder setRepositoryName(String repositoryName)
    {
        options.setRepositoryName(repositoryName);
        return this;
    }

    public GithubUpdaterOptions build()
    {
        return options;
    }

}

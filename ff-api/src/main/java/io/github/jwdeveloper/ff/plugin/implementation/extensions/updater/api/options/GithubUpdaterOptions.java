package io.github.jwdeveloper.ff.plugin.implementation.extensions.updater.api.options;

import lombok.Data;

@Data
public class GithubUpdaterOptions extends UpdaterOptions
{
    private String githubUserName;
    private String repositoryName;
}

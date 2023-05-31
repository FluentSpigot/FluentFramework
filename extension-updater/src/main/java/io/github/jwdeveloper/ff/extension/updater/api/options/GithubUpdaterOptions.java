package io.github.jwdeveloper.ff.extension.updater.api.options;

import lombok.Data;

@Data
public class GithubUpdaterOptions extends ProviderOptions
{
    private String githubUserName;
    private String repositoryName;
}

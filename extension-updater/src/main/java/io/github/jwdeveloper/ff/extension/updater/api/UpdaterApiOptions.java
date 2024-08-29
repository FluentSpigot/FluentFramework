package io.github.jwdeveloper.ff.extension.updater.api;
import io.github.jwdeveloper.ff.extension.updater.api.options.ProviderOptions;
import io.github.jwdeveloper.ff.extension.updater.api.options.builder.GithubOptionsBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionOptions;
import lombok.Data;

@Data
public class UpdaterApiOptions extends ExtensionOptions
{
    private GithubOptionsBuilder githubOptionsBuilder;

    private String configPath = "plugin.updater";

    private String commandName = "updater";

    private String permission = "updater";

    public UpdaterApiOptions()
    {
        githubOptionsBuilder = new GithubOptionsBuilder();
    }
    public GithubOptionsBuilder useGithub()
    {
        return githubOptionsBuilder;
    }

    public ProviderOptions getProviderOptions()
    {
        return githubOptionsBuilder.build();
    }
}

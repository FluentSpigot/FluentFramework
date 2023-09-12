package io.github.jwdeveloper.ff.tools;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.tools.FluentTaskAction;
import io.github.jwdeveloper.ff.tools.description.DescriptionToolAPI;
import io.github.jwdeveloper.ff.tools.description.options.BannerOptions;
import lombok.Data;

import java.nio.file.Path;
import java.util.function.Consumer;


public abstract class GenerateDescriptionTask extends FluentTaskAction
{
    private final DescriptionDto descriptionDto;

    public GenerateDescriptionTask()
    {
        descriptionDto = new DescriptionDto();
        onDescriptionConfig(descriptionDto);
    }

    @Override
    public Class<?> getJarScannerClass() {
        return descriptionDto.pluginMain;
    }

    @Override
    public String getPluginName() {
        return descriptionDto.pluginName;
    }


    public abstract void onDescriptionConfig(DescriptionDto descriptionDto);

    public abstract void onFluentPluginBuilding(FluentPluginBuilder builder);

    @Override
    public void onFluentPluginBuild(FluentPluginBuilder builder)
    {

        onFluentPluginBuilding(builder);
        builder.withExtension(DescriptionToolAPI.use(descriptionOptions ->
        {
            descriptionOptions.configureBanner(descriptionDto.bannerOptions);
            descriptionOptions.setInput(Path.of(FileUtility.getProgramPath(),descriptionDto.input));
            descriptionOptions.setOutput(Path.of(FileUtility.getProgramPath(),descriptionDto.output));
        }));
    }


    @Data
    public class DescriptionDto
    {
        private String pluginName;

        private Class<?> pluginMain;

        private String input;

        private String output;

        private Consumer<BannerOptions> bannerOptions = (e)->{};
    }

}

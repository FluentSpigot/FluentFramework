package io.github.jwdeveloper.ff.tools.description;
import io.github.jwdeveloper.descrabble.api.DescriptionDecorator;
import io.github.jwdeveloper.descrabble.framework.Descrabble;
import io.github.jwdeveloper.descrabble.plugin.github.DescrabbleGithub;
import io.github.jwdeveloper.descrabble.plugin.spigot.DescrabbleSpigot;
import io.github.jwdeveloper.ff.core.spigot.permissions.api.PermissionDto;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.tools.description.elements.BannerElement;
import io.github.jwdeveloper.ff.tools.description.elements.SectionWithImageElement;
import io.github.jwdeveloper.ff.tools.description.spigot.CommandsDocumentationGenerator;
import io.github.jwdeveloper.ff.tools.description.spigot.PermissionDocumentationGenerator;

public class DescriptionExtension implements FluentApiExtension {
    private final DescriptionOptions options;

    public DescriptionExtension(DescriptionOptions descriptionOptions) {
        this.options = descriptionOptions;
    }


    @Override
    public ExtentionPriority getPriority() {
        return ExtentionPriority.HIGH;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var banner = new BannerElement();



        var builder = Descrabble.create()
                .withTemplate(options.getInput())
                .withDecorator(banner)
                .withDecorator(getCommandsDecorator(fluentAPI))
                .withDecorator(getPermissionsDecorator(fluentAPI))
                .withDecorator(getConfigDecorator(fluentAPI))
                .withPlugin(DescrabbleGithub.plugin())
                .withPlugin(DescrabbleSpigot.plugin());


        options.getOnBuild().accept(builder);

        for (var decorator : options.getDecorators()) {
            builder.withDecorator(decorator);
        }
        for (var entry : options.getParameters().entrySet()) {
            builder.withVariable(entry.getKey(), entry.getValue().toString());
        }

        builder.build().generate(options.getOutput().toString());
    }

    private DescriptionDecorator getCommandsDecorator(FluentApiSpigot fluentApiSpigot) {
        var imageUrl = "https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/commands.png";
        var generator = new CommandsDocumentationGenerator();
        var content = generator.generate(fluentApiSpigot.commands().getSimpleCommands());
        return new SectionWithImageElement("commands", imageUrl, content);
    }

    private DescriptionDecorator getPermissionsDecorator(FluentApiSpigot fluentApiSpigot) {
        var imageUrl = "https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/permissions.png";

        var dto = new PermissionDto(FluentApiSpigot.class, fluentApiSpigot.permission().getPermissions());
        var permissions = new PermissionDocumentationGenerator(dto);
        var content = permissions.generate();
        return new SectionWithImageElement("permissions", imageUrl, content);
    }

    private DescriptionDecorator getConfigDecorator(FluentApiSpigot fluentApiSpigot) {
        var imageUrl = "https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/configuration.png";
        var content = fluentApiSpigot.config().configFile().saveToString();
        return new SectionWithImageElement("config", imageUrl, content);
    }
}

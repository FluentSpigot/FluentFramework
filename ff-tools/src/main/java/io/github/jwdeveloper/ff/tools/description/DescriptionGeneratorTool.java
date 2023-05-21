package io.github.jwdeveloper.ff.tools.description;

import io.github.jwdeveloper.descrabble.Descrabble;
import io.github.jwdeveloper.descrabble.api.DescriptionGenerator;
import io.github.jwdeveloper.descrabble.api.elements.Element;
import io.github.jwdeveloper.descrabble.api.elements.ElementFactory;
import io.github.jwdeveloper.descrabble.github.DescrabbleGithub;
import io.github.jwdeveloper.descrabble.spigot.DescrabbleSpigot;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;
import java.util.Set;

public class DescriptionGeneratorTool {
    private final Plugin plugin;
    private final FluentApiExtension extension;
    private final Path templatePath;

    public DescriptionGeneratorTool(Plugin plugin, FluentApiExtension extension, Path templatePath) {
        this.plugin = plugin;
        this.extension = extension;
        this.templatePath = templatePath;
    }

    public Set<Path> generate(String output) {
        var builder = FluentApiBuilder.create(plugin, extension);
        var fluentApiSpigot = builder.build();
        fluentApiSpigot.enable();

        fluentApiSpigot.disable();
        DescriptionGenerator descriptionGenerator = Descrabble.create()
                .withDecorator(this::createBanner)
                .withDecorator(this::createCommands)
                .withDecorator((a, b) -> createConfig(a, b, fluentApiSpigot))
                .withDecorator(this::createPermissions)
                .withPlugin(DescrabbleGithub.plugin())
                .withPlugin(DescrabbleSpigot.plugin())
                .withTemplate(templatePath)
                .build();

        return descriptionGenerator.generate(output);
    }


    private void createBanner(Element root, ElementFactory factory) {
        var banners = root.findElements("plugin-banner", true);
        if (banners.isEmpty()) {
            return;
        }

        var container = factory.customElement();
        var discordLink = factory.linkElement("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png", "");
        var githubLink = factory.linkElement("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png", "");
        var spigotLink = factory.linkElement("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png", "");
        container.addElement(discordLink, githubLink, spigotLink);

        for (var banner : banners) {
            banner.addElement(container);
        }
    }

    private void createConfig(Element root, ElementFactory factory, FluentApiSpigot apiSpigot) {
        var pluginConfigs = root.findElements("plugin-config", true);
        if (pluginConfigs.isEmpty()) {
            return;
        }
        var content = apiSpigot.config().configFile().saveToString();
        var configElement = factory.codeElement("yml", content);

        for (var pluginConfig : pluginConfigs) {
            pluginConfig.addElement(configElement);
        }
    }


    private void createPermissions(Element root, ElementFactory factory) {
        var banners = root.findElements("plugin-banner", true);
        if (banners.isEmpty()) {
            return;
        }

        var container = factory.customElement();
        var discordLink = factory.linkElement("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png", "");
        var githubLink = factory.linkElement("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png", "");
        var spigotLink = factory.linkElement("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png", "");
        container.addElement(discordLink, githubLink, spigotLink);

        for (var banner : banners) {
            banner.addElement(container);
        }
    }

    private void createCommands(Element root, ElementFactory factory) {
        var banners = root.findElements("plugin-banner", true);
        if (banners.isEmpty()) {
            return;
        }

        var container = factory.customElement();
        var discordLink = factory.linkElement("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png", "");
        var githubLink = factory.linkElement("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png", "");
        var spigotLink = factory.linkElement("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png", "");
        container.addElement(discordLink, githubLink, spigotLink);

        for (var banner : banners) {
            banner.addElement(container);
        }
    }
}

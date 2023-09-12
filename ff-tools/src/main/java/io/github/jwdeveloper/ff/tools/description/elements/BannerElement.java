package io.github.jwdeveloper.ff.tools.description.elements;

import io.github.jwdeveloper.descrabble.api.DescriptionDecorator;
import io.github.jwdeveloper.descrabble.api.elements.Element;
import io.github.jwdeveloper.descrabble.api.elements.ElementFactory;
import io.github.jwdeveloper.ff.tools.description.options.BannerOptions;

public class BannerElement implements DescriptionDecorator {

    private final BannerOptions bannerOptions;
    private final String path;

    public BannerElement(String path, BannerOptions bannerOptions) {
        this.bannerOptions = bannerOptions;
        this.path = path;
    }


    @Override
    public void decorate(Element root, ElementFactory factory) {
        var banners = root.findElements("banner", true);
        if (banners.isEmpty()) {
            return;
        }


        var bannerImage = factory.imageElement(bannerOptions.getBannerImage());
        bannerImage.removeProperty("open");
        var container = factory.getBuilder()
                .withName("container")
                .build();




        var supportLink = factory.imageElement(path + "/discord_medium2.svg", bannerOptions.getDonationUrl());
        var discordLink = factory.imageElement(path + "/discord_medium2.svg", bannerOptions.getDiscordUrl());
        var githubLink = factory.imageElement(path + "/discord_medium2.svg", bannerOptions.getGithubUrl());
        var spigotLink = factory.imageElement(path + "/discord_medium2.svg", bannerOptions.getSpigotUrl());

        discordLink.setProperty("width", "30%");
        supportLink.setProperty("width", "30%");

        githubLink.setProperty("width", "30%");
        githubLink.addTag("github-ignore");

        spigotLink.setProperty("width", "30%");
        spigotLink.addTag("spigot-ignore");
        container.addElement(supportLink, discordLink, githubLink, spigotLink);

        for (var banner : banners) {
            banner.addElement(bannerImage);
            banner.addElement(container);
        }
    }


}

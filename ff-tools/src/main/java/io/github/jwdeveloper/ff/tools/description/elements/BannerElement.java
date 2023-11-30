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
        bannerImage.addProperty("width","100%");
        var socialsContainer = factory.containerElement();




        var supportGif = factory.imageElement(path + "/support_old.gif", bannerOptions.getDonationUrl());
        supportGif.addTag("spigot-ignore");

        var supportSvg = factory.imageElement(path + "/support.svg", bannerOptions.getDonationUrl());
        supportSvg.addTag("github-ignore");

        var discordLink = factory.imageElement(path + "/discord.svg", bannerOptions.getDiscordUrl());
        var githubLink = factory.imageElement(path + "/github.svg", bannerOptions.getGithubUrl());
        var spigotLink = factory.imageElement(path + "/spigot.svg", bannerOptions.getSpigotUrl());

        discordLink.setProperty("width", "30%");
        supportGif.setProperty("width", "30%");

        githubLink.setProperty("width", "30%");
        githubLink.addTag("github-ignore");

        spigotLink.setProperty("width", "30%");
        spigotLink.addTag("spigot-ignore");
        socialsContainer.addElement(supportSvg,supportGif, discordLink, githubLink, spigotLink);

        for (var banner : banners) {
            banner.addElement(socialsContainer);
            banner.addElement(bannerImage);
        }
    }


}

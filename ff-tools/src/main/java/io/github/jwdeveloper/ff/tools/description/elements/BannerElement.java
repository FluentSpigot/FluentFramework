package io.github.jwdeveloper.ff.tools.description.elements;

import io.github.jwdeveloper.descrabble.api.DescriptionDecorator;
import io.github.jwdeveloper.descrabble.api.elements.Element;
import io.github.jwdeveloper.descrabble.api.elements.ElementBuilder;
import io.github.jwdeveloper.descrabble.api.elements.ElementFactory;
import io.github.jwdeveloper.descrabble.api.elements.ElementType;

public class BannerElement  implements DescriptionDecorator
{

    @Override
    public void decorate(Element root, ElementFactory factory) {
        var banners = root.findElements("banner", true);
        if (banners.isEmpty()) {
            return;
        }


        var bannerImage = factory.imageElement("https://raw.githubusercontent.com/jwdeveloper/TikTokLiveSpigot/master/webeditor/resources/banner.jpg");
        bannerImage.removeProperty("open");
        var container = factory.getBuilder()
                .withName("container")
                .build();



        var baseLinkBanner= "D:\\Git\\fluent-framework\\ff-tools\\resources\\banners";
       // baseLinkBanner = "https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media";

        var discordLink =  factory.imageElement(baseLinkBanner+"\\discord.png", "google.com");
        var githubLink = factory.imageElement(baseLinkBanner+"\\github.png", "google.com");
        var spigotLink =  factory.imageElement(baseLinkBanner+"\\support.png", "google.com");
        container.addElement(discordLink, githubLink, spigotLink);

        for (var banner : banners)
        {
            banner.addElement(bannerImage);
            banner.addElement(container);
        }
    }


}

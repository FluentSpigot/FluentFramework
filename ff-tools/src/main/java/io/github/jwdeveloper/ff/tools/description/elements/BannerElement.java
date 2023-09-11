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


        var bannerImage = getImage(factory.getBuilder(),"https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/banner.png","");

        var container = factory.getBuilder()
                .withName("container")
                .build();
        var discordLink = getImage(factory.getBuilder(),"https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png", "google.com");
        var githubLink =getImage(factory.getBuilder(),"https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png", "google.com");
        var spigotLink = getImage(factory.getBuilder(),"https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png", "google.com");
        container.addElement(discordLink, githubLink, spigotLink);

        for (var banner : banners)
        {
            banner.addElement(bannerImage);
            banner.addElement(container);
        }
    }


    public static Element getImage(ElementBuilder builder, String image, String redirect) {

        return builder.withName("image")
                .withType(ElementType.IMAGE)
                .withProperty("image", image)
                .withProperty("open", redirect)
                .build();
    }


}

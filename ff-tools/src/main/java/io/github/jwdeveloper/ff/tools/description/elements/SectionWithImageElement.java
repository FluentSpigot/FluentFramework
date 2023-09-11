package io.github.jwdeveloper.ff.tools.description.elements;

import io.github.jwdeveloper.descrabble.api.DescriptionDecorator;
import io.github.jwdeveloper.descrabble.api.elements.Element;
import io.github.jwdeveloper.descrabble.api.elements.ElementFactory;

public class SectionWithImageElement implements DescriptionDecorator {
    private final String sectionName;
    private final String image;
    private final String content;

    public SectionWithImageElement(String sectionName, String image, String content) {
        this.sectionName = sectionName;
        this.image = image;
        this.content = content;
    }

    @Override
    public void decorate(Element root, ElementFactory elementFactory) {
        var sections = root.findElements(sectionName, true);
        if (sections.isEmpty()) {
            return;
        }

        for (var section : sections) {

            var imageElement  = elementFactory.imageElement(image);
            imageElement.removeProperty("open");
            imageElement.setProperty("height","100%");

            var spoiler =elementFactory.spoilerElement("open " + sectionName);
            var codeElement =elementFactory.codeElement(content, "yml");


          /*  var spoiler = elementFactory.getBuilder()
                    .withName("spoiler")
                    .withType(ElementType.SPOILER)
                    .withProperty("title", "open " + sectionName)
                    .build();

            var codeElement = elementFactory.getBuilder()
                    .withName("code")
                    .withType(ElementType.CODE)
                    .withProperty("code", content)
                    .withProperty("language", "yml").build();
            */
            spoiler.addElement(codeElement);
            section.addElement(imageElement);
            section.addElement(spoiler);
        }
    }
}

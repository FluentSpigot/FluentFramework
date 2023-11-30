package io.github.jwdeveloper.ff.tools.description.elements;

import io.github.jwdeveloper.descrabble.api.DescriptionDecorator;
import io.github.jwdeveloper.descrabble.api.elements.Element;
import io.github.jwdeveloper.descrabble.api.elements.ElementFactory;
import io.github.jwdeveloper.descrabble.api.elements.ElementType;

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


            var container = elementFactory.containerElement();
            var spoilerContainer = elementFactory.containerElement();
            var imageElement = elementFactory.imageElement(image);
            imageElement.removeProperty("open");
            imageElement.setProperty("height", "100%");

            var spoiler = elementFactory.spoilerElement("open " + sectionName);


            var codeContainer = elementFactory.containerElement("left");
            var codeElement = elementFactory.getBuilder()
                    .withName("code")
                    .withType(ElementType.CODE)
                    .withProperty("code", content)
                    .withProperty("language", "yaml")
                    .build();


            codeContainer.addElement(codeElement);
            spoiler.addElement(codeContainer);
            spoilerContainer.addElement(spoiler);

            container.addElement(imageElement);
            container.addElement(spoilerContainer);

            section.addElement(container);
        }
    }
}

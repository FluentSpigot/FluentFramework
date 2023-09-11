package io.github.jwdeveloper.ff.tools.description.elements;

import io.github.jwdeveloper.descrabble.api.DescriptionDecorator;
import io.github.jwdeveloper.descrabble.api.elements.Element;
import io.github.jwdeveloper.descrabble.api.elements.ElementFactory;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

public class ConfigElement  implements DescriptionDecorator
{
    private FluentApiSpigot fluentApi;

    public ConfigElement(FluentApiSpigot fluentApi)
    {
        this.fluentApi = fluentApi;
    }

    @Override
    public void decorate(Element root, ElementFactory factory) {
        var pluginConfigs = root.findElements("plugin-config", true);
        if (pluginConfigs.isEmpty()) {
            return;
        }
        var content = fluentApi.config().configFile().saveToString();
        var configElement = factory.codeElement("yml", content);

        for (var pluginConfig : pluginConfigs) {
            pluginConfig.addElement(configElement);
        }
    }


}

package io.github.jwdeveloper.ff.animations.impl.parsers;

import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.impl.data.custom.NodeData;
import io.github.jwdeveloper.ff.animations.impl.data.custom.NodeDataMapper;
import io.github.jwdeveloper.ff.animations.impl.data.custom.NodeValue;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AnimationParser {
    private final static Pattern nodeNamePattern = Pattern.compile("\\[(.*?)\\]\\s+(.*)");
    private final static Pattern nodeValuesPattern = Pattern.compile("(\\w+):([^\\s]+)");
    private final NodeDataMapper mapper;

    public AnimationParser(NodeDataMapper nodeDataMapper) {
        this.mapper = nodeDataMapper;
    }

    public List<FluentAnimation> parse(ConfigurationSection configuration)
    {
        return List.of();
    }

    public List<AnimationNode> getNodes(List<String> content) {
        var result = new ArrayList<AnimationNode>();
        for (var line : content) {
            var nodeData = parseData(line);
            var node = mapper.map(nodeData);
            result.add(node);
        }
        return result;
    }


    public static NodeData parseData(String input) {
        var matcher = nodeNamePattern.matcher(input);

        if (matcher.find()) {
            var nodeType = matcher.group(1);
            var nodeData = new NodeData();
            nodeData.setName(nodeType);
            var valuesPart = matcher.group(2);
            var valueMatcher = nodeValuesPattern.matcher(valuesPart);
            while (valueMatcher.find()) {
                var nodeValue = new NodeValue(valueMatcher.group(1), valueMatcher.group(2));
                nodeData.addValue(nodeValue);
            }
            return nodeData;
        }
        return null;
    }
}

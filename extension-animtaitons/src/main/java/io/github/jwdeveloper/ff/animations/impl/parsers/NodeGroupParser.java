package io.github.jwdeveloper.ff.animations.impl.parsers;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.impl.nodes.WaitNode;
import io.github.jwdeveloper.ff.animations.impl.player.NodeGroup;

import java.util.ArrayList;
import java.util.List;

public class NodeGroupParser
{

    public List<NodeGroup> toNodeGroup(List<AnimationNode> nodes)
    {
        var result = new ArrayList<NodeGroup>();
        var currentGroup = new NodeGroup();
        currentGroup.setStartedMs(0);
        result.add(currentGroup);
        for(var node : nodes)
        {
            if(node instanceof WaitNode waitNode)
            {
                var startedTime = currentGroup.getStartedMs();
                startedTime += waitNode.getStartMs();

                currentGroup = new NodeGroup();
                currentGroup.setStartedMs(startedTime);
                result.add(currentGroup);
                continue;
            }
            currentGroup.addNode(node);
        }
        return result;
    }
}

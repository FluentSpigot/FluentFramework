package io.github.jwdeveloper.ff.animations.impl.data;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.impl.nodes.WaitNode;
import io.github.jwdeveloper.ff.animations.impl.player.NodeGroup;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AnimationData {
    private String name;
    private int totalTime;
    private Map<String, List<NodeGroup>> nodeGroups = new HashMap<>();

    public void addGroup(String name, List<NodeGroup> groups) {
        nodeGroups.put(name, groups);
    }


    public List<NodeGroup> getBone(String name)
    {
        if(!nodeGroups.containsKey(name))
        {
            return List.of();
        }

        return nodeGroups.get(name);
    }

    public List<NodeGroup> getDefaultGroup()
    {
        if(nodeGroups.containsKey("default"))
        {
            return nodeGroups.get("default");
        }
        return List.of();
    }
}

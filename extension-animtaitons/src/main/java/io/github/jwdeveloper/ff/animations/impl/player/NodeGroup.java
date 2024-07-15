package io.github.jwdeveloper.ff.animations.impl.player;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class NodeGroup
{
    List<AnimationNode> nodes = new ArrayList<>();
    int startedMs;

    public boolean hasNodes()
    {
        return !nodes.isEmpty();
    }

    public void addNode(AnimationNode animationNode)
    {
        this.nodes.add(animationNode);
    }
}

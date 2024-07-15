package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import org.junit.Assert;

import java.util.List;
import java.util.function.Consumer;

public class NodesValidator {
    private List<AnimationNode> nodes;

    public NodesValidator(List<AnimationNode> nodes) {
        this.nodes = nodes;
    }

    public static NodesValidator check(List<AnimationNode> nodes) {
        return new NodesValidator(nodes);
    }

    public <T extends AnimationNode> NodesValidator hasNode(Class<T> clazz, Consumer<T> validator) {

        var node = nodes.stream().filter(e -> e.getClass().equals(clazz)).findFirst();
        Assert.assertTrue(node.isPresent());
        var value = node.get();
        validator.accept((T)value);
        return this;
    }

    public NodesValidator hasNode(Class<? extends AnimationNode> clazz, int index) {
        return this;
    }

    public NodesValidator hasNodes(int count) {
        return this;
    }
}

package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.impl.nodes.*;
import io.github.jwdeveloper.ff.animations.impl.data.custom.NodeDataMapper;
import io.github.jwdeveloper.ff.animations.impl.parsers.AnimationParser;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AnimationNodeParserTest {


    @Test
    public void shouldParse() {
        var parser = new AnimationParser(new NodeDataMapper());
        var nodes = parser.getNodes(getParseContent());
        NodesValidator.check(nodes)
                .hasNode(TransformNode.class, waitNode ->
                {
                    Assert.assertEquals(new Vector(1.1, 2.1, 3.1), waitNode.getVector());
                })
                .hasNode(RotateNode.class, waitNode ->
                {
                    Assert.assertEquals(90, waitNode.getAngle());
                    Assert.assertEquals(new Vector(1.1, 2.1, 3.1), waitNode.getVector());
                })
                .hasNode(ScaleNode.class, waitNode ->
                {
                    Assert.assertEquals(new Vector(1.1, 2.1, 3.1), waitNode.getVector());
                })
                .hasNode(WaitNode.class, waitNode ->
                {
                    Assert.assertEquals(12, waitNode.getTicks());
                })
                .hasNode(TeleportNode.class, waitNode ->
                {
                    Assert.assertEquals(new Vector(1.1, 2.1, 3.1), waitNode.getVector());
                })
                .hasNode(ParticleNode.class, waitNode ->
                {
                    Assert.assertEquals(new Vector(1.1, 2.1, 3.1), waitNode.getOffset());
                    Assert.assertEquals(12, waitNode.getCount());
                    Assert.assertEquals(Particle.CLOUD, waitNode.getParticle());
                })
                .hasNodes(2);
    }


    private List<String> getParseContent() {
        return List.of(
                "[t] x:1.1 y:2.1 z:3.1",
                "[r] x:1.1 y:2.1 z:3.1 angle:90",
                "[s] x:1.1 y:2.1 z:3.1",
                "[w] ticks:12",
                "[tp] x:1.1 y:2.1 z:3.1",
                "[p] x:1.1 y:2.1 z:3.1 count:12 particle:cloud",
                "[sound] sound:BLOCK_STONE_HIT volume:1.1 pitch:1.1"
        );
    }

}
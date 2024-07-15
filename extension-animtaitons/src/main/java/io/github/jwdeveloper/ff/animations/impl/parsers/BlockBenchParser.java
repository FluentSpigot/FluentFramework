package io.github.jwdeveloper.ff.animations.impl.parsers;

import com.google.gson.*;
import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.impl.Animation;
import io.github.jwdeveloper.ff.animations.impl.data.AnimationData;
import io.github.jwdeveloper.ff.animations.impl.nodes.RotateBlockBench;
import io.github.jwdeveloper.ff.animations.impl.nodes.ScaleNode;
import io.github.jwdeveloper.ff.animations.impl.nodes.TransformNode;
import io.github.jwdeveloper.ff.animations.impl.player.NodeGroup;
import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.function.Function;

public class BlockBenchParser {
    private final Gson gson;

    public BlockBenchParser(Gson gson) {
        this.gson = gson;
    }

    public List<FluentAnimation> parse(String content) {
        var result = new ArrayList<FluentAnimation>();
        var element = JsonParser.parseString(content);
        var root = element.getAsJsonObject();
        var animations = root.get("animations").getAsJsonObject();
        for (var entry : animations.entrySet()) {
            var actionResult = handelSingleAnimation(entry.getKey(), entry.getValue());
            if (actionResult.isSuccess()) {
                result.add(actionResult.getObject());
            } else {
                FluentLogger.LOGGER.warning("This is action result warning", actionResult.getMessage());
            }
        }
        return result;
    }


    private ActionResult<FluentAnimation> handelSingleAnimation(String name, JsonElement value) {

        var root = value.getAsJsonObject();
        var length = root.get("animation_length").getAsFloat();
        /*   var loop = root.get("loop").getAsBoolean();*/
        var bones = root.get("bones").getAsJsonObject();

        var data = new AnimationData();
        data.setTotalTime((int) (length * 1000));
        data.setName(name);
        for (var entry : bones.entrySet()) {
            var boneName = entry.getKey();
            var boneNodes = getNodes(entry.getValue());
            data.addGroup(boneName, boneNodes);
        }
        var animation = new Animation(data);
        return ActionResult.success(animation);
    }

    public List<NodeGroup> getNodes(JsonElement element) {
        var root = element.getAsJsonObject();

        var positions = mapToNodes(root.get("position"), TransformNode::new);
        var scales = mapToNodes(root.get("scale"), ScaleNode::new);
        var rotations = mapToNodes(root.get("rotation"), RotateBlockBench::new);
        var maps = new ArrayList<Map<Integer, AnimationNode>>();
        maps.add(rotations);
        maps.add(positions);
        maps.add(scales);
        var commonMap = new TreeMap<Integer, List<AnimationNode>>();
        for (var map : maps) {
            map.forEach((timeStamp, nodes) ->
            {
                commonMap.computeIfAbsent(timeStamp, integer -> new ArrayList<>());
                commonMap.computeIfPresent(timeStamp, (integer, _nodes) ->
                {
                    _nodes.add(nodes);
                    return _nodes;
                });
            });
        }
        var groups = new ArrayList<NodeGroup>();
        commonMap.forEach((integer, nodes) ->
        {
            var group = new NodeGroup();
            group.setStartedMs(integer);
            group.setNodes(nodes);
            groups.add(group);
        });
        return groups;
    }

    private Map<Integer, AnimationNode> mapToNodes(JsonElement element, Function<Vector, AnimationNode> func) {
        var result = new TreeMap<Integer, AnimationNode>();
        if (element == null) {
            return result;
        }

        if (element.isJsonArray()) {
            var vector = toVector(element.getAsJsonArray());
            var node = func.apply(vector);
            result.put(0, node);
            return result;
        }

        var root = element.getAsJsonObject();
        for (var entry : root.entrySet()) {
            var milliseconds = (int) (Float.parseFloat(entry.getKey()) * 1000);
            var vector = toVector(entry.getValue().getAsJsonArray());
            var node = func.apply(vector);
            result.put(milliseconds, node);
        }
        return result;
    }

    private Vector toVector(JsonArray array) {
        Float[] floatArray = new Float[array.size()];
        for (int i = 0; i < array.size(); i++) {
            floatArray[i] = array.get(i).getAsFloat();
        }
        return new Vector(floatArray[0], floatArray[1], floatArray[2]);
    }
}

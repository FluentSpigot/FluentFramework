package io.github.jwdeveloper.ff.models.impl.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.source.tree.Tree;
import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModel;
import io.github.jwdeveloper.ff.models.api.data.Bone;
import io.github.jwdeveloper.ff.models.api.data.BoneCube;
import io.github.jwdeveloper.ff.models.impl.DisplayBone;
import io.github.jwdeveloper.ff.models.impl.DisplayModel;
import io.github.jwdeveloper.ff.models.impl.DisplayModelData;
import org.bukkit.util.Vector;

import java.util.*;

public class BlockBenchModelParser {

    public ActionResult<FluentDisplayModel> parse(String json) {
        var element = JsonParser.parseString(json);
        var root = element.getAsJsonObject();
        var geometries = root.get("minecraft:geometry").getAsJsonArray();
        var data = new DisplayModelData();
        for (var geometry : geometries) {
            var geometryElement = geometry.getAsJsonObject();
            handleDescription(data, geometryElement);
            data.setBones(handleBones(geometryElement));
        }
        var displayModel = new DisplayModel(data);
        return ActionResult.success(displayModel);
    }

    private void handleDescription(DisplayModelData data, JsonObject jsonObject) {

        var description = jsonObject.get("description").getAsJsonObject();
        var name = description.get("identifier").getAsString();
        data.setName(name);
    }

    private Map<String, Bone> handleBones(JsonObject jsonObject) {
        var array = jsonObject.get("bones").getAsJsonArray();
        var bones = new TreeMap<String, Bone>();
        for (var jsonBone : array) {
            var bone = handleSingleBone(jsonBone);
            bones.put(bone.getName(), bone);
        }
        return bones;
    }

    private Bone handleSingleBone(JsonElement jsonObject) {
        var root = jsonObject.getAsJsonObject();
        var name = root.get("name").getAsString();

        var pivot = new Vector(0, 0, 0);
        List cubes = new ArrayList<BoneCube>();
        if (root.has("pivot")) {
            pivot = toVector(root.get("pivot").getAsJsonArray());
        }
        if (root.has("cubes")) {
            cubes = handleCubes(root.get("cubes").getAsJsonArray());
        }

        return new DisplayBone(cubes, pivot, name);
    }


    private List<BoneCube> handleCubes(JsonArray array) {

        var result = new ArrayList<BoneCube>();
        for (var element : array) {
            var root = element.getAsJsonObject();
            var boneCube = new BoneCube();

            if (root.has("origin")) {

                var transform = toVector(root.get("origin").getAsJsonArray());

                transform.setX(transform.getX() * -1);
                // transform.setZ(transform.getZ() * -1);
                boneCube.setTransform(transform);
            }
            if (root.has("size")) {
                boneCube.setScale(toVector(root.get("size").getAsJsonArray()));
            }
            if (root.has("rotation")) {
                boneCube.setRotation(toVector(root.get("rotation").getAsJsonArray()).multiply(-1));
            }
            result.add(boneCube);
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

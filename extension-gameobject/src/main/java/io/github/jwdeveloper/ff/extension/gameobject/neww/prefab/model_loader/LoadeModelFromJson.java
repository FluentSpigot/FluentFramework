package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.model_loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.joml.Matrix4f;
import org.joml.Vector4f;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadeModelFromJson
{
    public List<DisplayModel> FromJson(String jsonFile)
    {
        var result = new ArrayList<DisplayModel>();
        var json= JsonParser.parseString(jsonFile);
        var list = json.getAsJsonArray();
        for(var element : list.asList())
        {

          var parsed = parseSingleElement(element.getAsJsonObject());
            result.add(parsed);
        }
      //  var parsed = parseSingleElement(list.asList().get(1).getAsJsonObject());
      //    result.add(parsed);

        return result;
    }

    public DisplayModel parseSingleElement(JsonObject jsonElement)
    {
        //Display
        var id = handleDisplayType(jsonElement.get("id"));
        var transformation  = handleTransformation(jsonElement.getAsJsonArray("transformation"));
        var nbt  = jsonElement.get("nbt").getAsString();
        var meta = handleDisplayMeta(jsonElement.get("block_state"));

        var result = new DisplayModel();
        result.setOrigin(new Vector(0,0,0));
        result.setDisplayType(id);
        result.setNbt(nbt);
        result.setTransformationMatrix(transformation);
        result.setDisplayMeta(meta);
        return result;
    }

    public EntityType handleDisplayType(JsonElement jsonElement)
    {
       var value = jsonElement.getAsString();
       value = value.replace("minecraft:", StringUtils.EMPTY);
       return  EntityType.valueOf(value.toUpperCase());
    }

    public Matrix4f handleTransformation(JsonArray jsonElement)
    {
        var elements = jsonElement.asList();
        var vectores = new ArrayList<Vector4f>();

        for(var i =0;i<4;i+=1)
        {
            float a = elements.get(i).getAsFloat();
            float b = elements.get(i+4).getAsFloat();
            float c = elements.get(i+8).getAsFloat();
            float d = elements.get(i+12).getAsFloat();
            vectores.add(new Vector4f(a,b,c,d));
        }
        return new Matrix4f(vectores.get(0),vectores.get(1),vectores.get(2),vectores.get(3));
    }

    public DisplayMeta handleDisplayMeta(JsonElement jsonElement)
    {
        var name = jsonElement.getAsJsonObject().get("Name").getAsString();
        var materialName = jsonElement.getAsJsonObject().get("SimpleName").getAsString().toUpperCase();
        var properties = jsonElement.getAsJsonObject().get("Properties");
        var meta = new DisplayMeta();
        meta.setName(name);
        meta.setMaterial(Material.valueOf(materialName));
        meta.setProperties(new HashMap<>());
        return meta;
    }

    @Data
    public class DisplayModel
    {
        public EntityType displayType;
        public DisplayMeta displayMeta;
        public Vector origin;
        public Matrix4f transformationMatrix;
        public String nbt;

        public List<DisplayModel> children = new ArrayList<>();
    }

    @Data
    public class DisplayMeta
    {
        public String name;
        public Material material;
        public Map<String, String> properties;


    }
}

package io.github.jwdeveloper.ff.extension.resourcepack.tools;

import com.google.gson.JsonParser;
import io.github.jwdeveloper.ff.core.logger.plugin.SimpleLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.extension.resourcepack.api.models.ResourceItemModel;
import io.github.jwdeveloper.ff.tools.files.code.ClassCodeBuilder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ItemModelsClassGenerator {
    private final String input;
    private final String output;
    private final SimpleLogger logger;
    private final Class<ResourceItemModel> modelClass;

    public ItemModelsClassGenerator(String input, String output) {
        this.input = input;
        this.output = output;
        logger = new SimpleLogger();
        modelClass = ResourceItemModel.class;
    }


    public void generate() {
        try {
            tryGenerate();
        } catch (Exception e) {
            throw new RuntimeException("Unable to generate ResourcepackItemModels",e);
        }

    }


    public void tryGenerate() throws IOException {
        var resourceMeta = loadResourceMeta();
        var root = new ClassCodeBuilder();


        var find = "java";
        var index = output.indexOf("java") + find.length()+1;
        var packageName = output.substring(index);
        packageName = packageName.replace("\\",".");


        root.setPackage(packageName);

        root.addImport(modelClass.getPackageName()+"."+modelClass.getSimpleName());
        root.addImport("org.bukkit.Color");
        root.addImport("org.bukkit.Material");
        root.addImport("org.bukkit.inventory.ItemStack");
        root.addImport("org.bukkit.inventory.meta.LeatherArmorMeta");

        root.setModifiers("public");
        root.setClassName("PluginModels");

        root.addField(e ->
        {
            e.setModifier("private static final");
            e.setType("Material");
            e.setName("MATERIAL");
            e.setValue("Material." + resourceMeta.getItemStackName());
        });

        makeFields(resourceMeta.getModels(), root);
        var content = root.build();
        FileUtility.saveClassFile(content, output,"PluginItemModels");
    }


    public void makeFields(List<ModelMeta> modelMetas, ClassCodeBuilder builder) {

        var clazzName = modelClass.getSimpleName();
        for(var model : modelMetas)
        {
            builder.addField(field ->
            {
                field.setModifier("public static final");
                field.setType(clazzName);
                field.setName(model.getPath());
                field.setValue("new ResourceModel(" + model.getCustomModelId() + ", \"" + model.getName() + "\", MATERIAL)");
            });
        }
    }

    public void makeFields(ConfigurationSection section, ClassCodeBuilder builder) {
        if (section == null) {
            return;
        }

        if (section.contains("MODEL")) {
            builder.addField(methodCodeGenerator ->
            {
                methodCodeGenerator.setModifier("public final static");
                methodCodeGenerator.setType("ResourceModel");
                methodCodeGenerator.setName("getId");
                methodCodeGenerator.setName(section.getName().toUpperCase());
                var id = section.get("id");
                var name = section.get("name");

                methodCodeGenerator.setValue("new ResourceModel(" + id + ", \"" + name + "\")");
            });
            return;
        }

        for (var key : section.getKeys(false)) {
            if (key.contains("MODEL")) {
                continue;
            }
            var subClass = new ClassCodeBuilder();
            var className = key;
            className = className.replace("_", "");
            className = className.trim();
            className = StringUtils.capitalize(className);
            subClass.setClassName(className);
            subClass.setModifiers("public static");

            makeFields(section.getConfigurationSection(key), builder);
        }
    }

    private YamlConfiguration loadToYml() throws IOException {
        var config = new YamlConfiguration();
        var content = FileUtility.loadFileContent(Path.of(input).toString());
        var json = new JsonParser().parse(content).getAsJsonObject();


        var overrides = json.getAsJsonArray("overrides");
        for (var element : overrides) {
            var jsonElement = element.getAsJsonObject();
            var modelPath = jsonElement.get("model").getAsString();
            var customId = jsonElement.get("predicate").getAsJsonObject().get("custom_model_data").getAsInt();
            var displayName = modelPath.split("\\/");
            var name = displayName[displayName.length - 1];
            name = name.replaceAll("\\_", " ");

            var yamlPath = modelPath.replace("/", ".");
            yamlPath = yamlPath.replace("item.jw.", " ");
            config.set(yamlPath + ".MODEL", true);
            config.set(yamlPath + ".name", name);
            config.set(yamlPath + ".id", customId);
        }

        return config;
    }

    private ResourceMeta loadResourceMeta() throws IOException {
        var result = new ResourceMeta();
        var content = FileUtility.loadFileContent(Path.of(input).toString());
        var json = new JsonParser().parse(content).getAsJsonObject();

        var itemName = Paths.get(input)
                .getFileName()
                .toString()
                .replace(".json", "")
                .toUpperCase();

        result.setItemStackName(itemName);

        for (var element : json.getAsJsonArray("overrides")) {
            var jsonElement = element.getAsJsonObject();
            var modelPath = jsonElement.get("model").getAsString();
            var customId = jsonElement.get("predicate").getAsJsonObject().get("custom_model_data").getAsInt();
            var displayName = modelPath.split("\\/");
            var name = displayName[displayName.length - 1];
            name = name.replaceAll("\\_", " ");
            name = StringUtils.capitalize(name);

            var yamlPath = modelPath.replace("/", "_");
            yamlPath = yamlPath.replace("item_jw_", "");
            yamlPath = yamlPath.toUpperCase();

            var model = new ModelMeta();
            model.setCustomModelId(customId);
            model.setPath(yamlPath);
            model.setName(name);


            result.getModels().add(model);
        }
        return result;
    }


    public static class Tree {
        private ClassCodeBuilder root;
        private List<ClassCodeBuilder> childs = new ArrayList<>();

        public Tree(ClassCodeBuilder root) {
            this.root = root;
        }

        public void addChild(ClassCodeBuilder child) {
            childs.add(child);
        }
    }

    @Data
    public static class ResourceMeta {
        private String itemStackName;

        public List<ModelMeta> models = new ArrayList<>();
    }

    @Data
    public static class ModelMeta {
        private int customModelId;

        private String path;

        private String name;
    }

    /*
       public class [Name]
       {
          private static final Material MATERIAL;

          public static final ResourceModel BENCH = new ResourceModel([id], [name], MATERIAL);

          public static class PIANO
          {
              public static final ResourceModel GRAND_PIANO = new ResourceModel([id], [name], MATERIAL);
          }
       }
    /*
    /*
    {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": "minecraft:item/leather_horse_armor"
    },
    "overrides": [
        { "predicate": {  "custom_model_data": 222222200 } , "model": "item/jw/bench" },
        { "predicate": {  "custom_model_data": 222222201 } , "model": "item/jw/flyingnote" },
        { "predicate": {  "custom_model_data": 222222202 } , "model": "item/jw/icons/icon" },
        { "predicate": {  "custom_model_data": 222222203 } , "model": "item/jw/key/piano_black_key" },
        { "predicate": {  "custom_model_data": 222222204 } , "model": "item/jw/key/piano_black_key_down" },
        { "predicate": {  "custom_model_data": 222222205 } , "model": "item/jw/key/piano_key" },
        { "predicate": {  "custom_model_data": 222222206 } , "model": "item/jw/key/piano_key_down" },
        { "predicate": {  "custom_model_data": 222222207 } , "model": "item/jw/pedal/piano_pedal" },
        { "predicate": {  "custom_model_data": 222222208 } , "model": "item/jw/pedal/piano_pedal_down" },
        { "predicate": {  "custom_model_data": 222222209 } , "model": "item/jw/pianist/pianist" },
        { "predicate": {  "custom_model_data": 222222210 } , "model": "item/jw/pianist/pianist_hands" },
        { "predicate": {  "custom_model_data": 222222211 } , "model": "item/jw/pianist/pianist_head" },
        { "predicate": {  "custom_model_data": 222222212 } , "model": "item/jw/piano/electric_piano" },
        { "predicate": {  "custom_model_data": 222222213 } , "model": "item/jw/piano/grand_piano" },
        { "predicate": {  "custom_model_data": 222222214 } , "model": "item/jw/piano/grand_piano_close" },
        { "predicate": {  "custom_model_data": 222222215 } , "model": "item/jw/piano/up_right_piano_close" }
    ]
}
     */
}

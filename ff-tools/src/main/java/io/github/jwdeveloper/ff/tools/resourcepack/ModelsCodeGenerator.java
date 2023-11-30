package io.github.jwdeveloper.ff.tools.resourcepack;

import com.google.gson.JsonParser;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.tools.files.code.ClassCodeBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModelsCodeGenerator {




    public static void generateClassFile(Consumer<ModelsOptions> consumer) throws IOException {
        var options = new ModelsOptions();
        consumer.accept(options);
        var content = generateAsString(options);
        FileUtility.saveClassFile(content, options.getOutputClassPath(), options.getOutputClassName());
    }

    public static String generateAsString(ModelsOptions options) throws IOException {
        var yaml = loadToYml(options);

        //D:\Git\Spigot\AdventureBackpack\src\main\java\io\github\jw\spigot\backpack\models
        var namespace = options.getOutputClassPath().replace("\\",".");
        var index = namespace.indexOf("java");
        namespace = namespace.substring(index+5);

        var root = new ClassCodeBuilder();
        root.setClassName(options.getOutputClassName());
        root.setPackage(namespace);
        root.setModifiers("public");
        root.addImport("org.bukkit.Color");
        root.addImport("org.bukkit.Material");
        root.addImport("org.bukkit.inventory.ItemStack");
        root.addImport("org.bukkit.inventory.meta.LeatherArmorMeta");
        root.addImport("java.util.Map");
        root.addImport("ava.util.TreeMap");


        root.addField(e ->
        {
            e.setModifier("private static final");
            e.setType("Material");
            e.setName("MATERIAL");
            e.setValue("Material." + options.getMaterialName().toUpperCase());
        });


        root.addClass("""
                    public record ResourceModel(int id, String name) {
                     
                             public Material getMaterial() {
                                 return MATERIAL;
                             }
                     
                             public ItemStack toItemStack() {
                                 return toItemStack(Color.WHITE);
                             }
                             public ItemStack toItemStack(Color color) {
                                     var itemStack = new ItemStack(getMaterial());
                                     var meta = itemStack.getItemMeta();
                                     if(meta == null)
                                      {
                                         return itemStack;
                                      }
                                      meta.setDisplayName(name);
                                      meta.setCustomModelData(id);
                                      if(meta instanceof LeatherArmorMeta leatherArmorMeta)
                                      {
                                         leatherArmorMeta.setColor(color);
                                      }
                                      itemStack.setItemMeta(meta);
                                      return itemStack;
                             }
                             
                                public static Map<String,ResourceModel> getAllModels()
                                 {
                                     var map = new TreeMap<String,ResourceModel>();
                                     var clazz = PluginModels.class;
                                     for(var field : clazz.getDeclaredFields())
                                     {
                                         if(!field.getType().equals(io.github.jw.spigot.backpack.models.PluginModels.ResourceModel.class))
                                         {
                                           continue;
                                         }
                             
                                         try
                                         {
                                             var name = field.getName();
                                             var value = (ResourceModel)field.get(null);
                                             map.put(name,value);
                                         }
                                         catch (Exception e)
                                         {
                                             continue;
                                         }
                                     }
                                     return map;
                                 }
                         }
                """);
        makeFields(yaml, root);
        return root.build();
    }


    public static void makeFields(ConfigurationSection section, ClassCodeBuilder builder) {
        if (section == null) {
            return;
        }

        if (section.contains("MODEL")) {
            builder.addField(methodCodeGenerator ->
            {
                methodCodeGenerator.setModifier("public static");
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
            // FluentLogger.LOGGER.success("CLASS", key);
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
            // builder.addClass(subClass.build());
        }
    }

    private static YamlConfiguration loadToYml(ModelsOptions options) throws IOException {
        var config = new YamlConfiguration();

        var content = FileUtility.loadFileContent(options.getInputFile());
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


    public class Tree {
        private ClassCodeBuilder root;
        private List<ClassCodeBuilder> childs = new ArrayList<>();


        public Tree(ClassCodeBuilder root) {
            this.root = root;
        }

        public void addChild(ClassCodeBuilder child) {
            childs.add(child);
        }
    }

}

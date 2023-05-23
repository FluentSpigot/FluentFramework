package io.github.jwdeveloper.ff.tools.languages;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class LanguageClassCodeGeneratorTask
{
    public static void generate(String input, String output) {
        try
        {
            generate(input, output, "Translations");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void generate(String input, String output, String clazzName) throws IOException, IllegalAccessException {
        output = Path.of(output,"generated").toString();
        FileUtility.ensurePath(output);
        var filePath = output + FileUtility.separator() + clazzName + ".java";
        var file = new File(filePath);
        var configuration = YamlConfiguration.loadConfiguration(new File(input));

        var builder = new MessageBuilder();


        var packageName = output.replace("\\", ".");
        var startIndex = packageName.indexOf("java");
        startIndex = packageName.indexOf(".", startIndex);
        packageName = packageName.substring(startIndex + 1);

        builder.text("package ").text(packageName).text(";").newLine();
        addClass(builder, configuration, 0, clazzName, true);

        var writer = new FileWriter(file);
        var content = builder.toString();
        writer.write(content);
        writer.close();
    }


    private static void addClass(MessageBuilder builder, ConfigurationSection root, int offset, String name, boolean isRoot) {
        if (!isRoot) {
            name = name.toUpperCase();
            name = name.replace('-', '_');
        }

        builder.newLine();

        if (!isRoot) {
            builder.space(offset).text("public static class ").text(name).newLine();
        } else {
            builder.space(offset).text("public class ").text(name).newLine();
        }


        builder.space(offset).text("{").newLine();
        for (var permission : root.getKeys(false)) {
            var section = root.getConfigurationSection(permission);
            if (section != null) {
                addClass(builder, section, offset + 4, permission, false);
            } else {
                var properyPath = root.getCurrentPath()+"."+permission;
                var value = root.getString(permission);
                addProperty(builder, permission, properyPath,value, offset);
            }
         }
        builder.space(offset).text("}");
        builder.newLine();
    }

    private static void addProperty(MessageBuilder builder, String name, String properyPath,String value, int offset) {
        name = name.toUpperCase();
        name = name.replace('-', '_');
        builder.newLine();
        builder.space(offset+4).textNewLine("// "+value);
        builder.space(offset + 4).text("public static final String ").text(name).text(" = \"").text(properyPath).text("\";").newLine();
    }
}

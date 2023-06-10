package io.github.jwdeveloper.ff.tools.languages.code;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.tools.files.code.ClassCodeBuilder;
import io.github.jwdeveloper.ff.tools.files.code.ConstructorCodeGenerator;
import io.github.jwdeveloper.ff.tools.files.code.MethodCodeGenerator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LanguageDependecyCodeGeneratorTask {

    public static void generate(String input, String output) {
        try {
            var clazzName = "Translations";
            output = Path.of(output, "generated").toString();
            FileUtility.ensurePath(output);
            var filePath = output + FileUtility.separator() + clazzName + ".java";
            var file = new File(filePath);
            var configuration = YamlConfiguration.loadConfiguration(new File(input));
            var packageName = output.replace("\\", ".");
            var startIndex = packageName.indexOf("java");
            startIndex = packageName.indexOf(".", startIndex);
            packageName = packageName.substring(startIndex + 1);


            var content = generate(configuration, clazzName, packageName);
            var writer = new FileWriter(file);
            writer.write(content.build());
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ClassCodeBuilder generate(ConfigurationSection root, String clazzName, String packageName) {
        var currentClass = new ClassCodeBuilder();
        if (StringUtils.isNotNullOrEmpty(packageName)) {
            currentClass.setModifiers("public final");
            currentClass.setPackage(packageName);
            currentClass.addImport("io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator");
        }
        else
        {
            currentClass.setModifiers("public static");
        }


        currentClass.setClassName(clazzName);

        currentClass.addField(e ->
        {
            e.setModifier("private final");
            e.setType("FluentTranslator");
            e.setName("translator");
        });

        var constructor = new ConstructorCodeGenerator();
        constructor.setModifiers("public");
        constructor.addParameter("FluentTranslator translator");
        constructor.addBodyLine("this.translator = translator;");


        var result = new ArrayList<ClassCodeBuilder>();
        result.add(currentClass);

        var sortedKeys = root.getKeys(false)
                .stream()
                .sorted((o1, o2) -> Boolean.compare(root.isConfigurationSection(o1), root.isConfigurationSection(o2)))
                .toList();

        for (var path : sortedKeys) {
            if (root.isConfigurationSection(path)) {
                var section = root.getConfigurationSection(path);
                var classTypeName = "CLASS_" + StringUtils.capitalize(path.toLowerCase());
                var propertyName = path.toUpperCase();


                currentClass.addField(fieldCodeGenerator ->
                {
                    fieldCodeGenerator.setModifier("public final");
                    fieldCodeGenerator.setType(classTypeName);
                    fieldCodeGenerator.setName(propertyName);
                });
                constructor.addBodyLine(e ->
                {
                    e.text(propertyName).text(" = new ").text(classTypeName).text("(translator);");
                });

                var subClazz = generate(section, classTypeName, null);
                currentClass.addClass(subClazz);
                continue;
            }

            var translationText = root.getString(path);
            var parameters = getParameters(translationText);
            var fullPath = StringUtils.isNullOrEmpty(root.getCurrentPath()) ? path : root.getCurrentPath() + "." + path;
            var propertyName = path.toUpperCase().replaceAll("-","_");
            var comment = fullPath + ": " + translationText;
            if (parameters.isEmpty()) {

                currentClass.addField(fieldCodeGenerator ->
                {
                    fieldCodeGenerator.addComment(comment);
                    fieldCodeGenerator.setModifier("public final");
                    fieldCodeGenerator.setType(String.class);
                    fieldCodeGenerator.setName(propertyName);
                });
                constructor.addBodyLine(e ->
                {
                    e.text(propertyName).text(" = translator.get(\"").text(fullPath).text("\");");
                });
                continue;
            }

            var method = new MethodCodeGenerator();
            method.setModifiers("public");
            method.setType(String.class);
            method.setName(propertyName);
            method.addComment(comment);
            for (var parameter : parameters) {
                method.addParameter("final", Object.class, parameter);
            }
            method.setBody(e ->
            {
                e.text("return translator.get(\"").text(fullPath).text("\"");
                e.text(",");
                var i = 0;
                for (var parameter : parameters) {
                    e.text(parameter);
                    if (i != parameters.size() - 1) {
                        e.text(",");
                    }
                    i++;
                }
                e.text(");");
            });
            currentClass.addMethod(method);
        }

        currentClass.addConstructor(constructor);
        return currentClass;
    }

    private static List<String> getParameters(String input) {
        var result = new ArrayList<String>();
        var pattern = "\\{\\{([^\\}]+)\\}\\}";
        var regexPattern = Pattern.compile(pattern);
        var matcher = regexPattern.matcher(input);
        while (matcher.find()) {
            var matchedPattern = matcher.group(1);
            matchedPattern = StringUtils.removeWhitespaces(matchedPattern);
            result.add(matchedPattern);
        }
        return result;
    }


}

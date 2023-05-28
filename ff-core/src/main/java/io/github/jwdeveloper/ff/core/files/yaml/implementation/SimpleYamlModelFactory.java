package io.github.jwdeveloper.ff.core.files.yaml.implementation;

import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.yaml.api.YamlModelFactory;
import io.github.jwdeveloper.ff.core.files.yaml.api.YamlSymbols;
import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import io.github.jwdeveloper.ff.core.files.yaml.api.models.YamlContent;
import io.github.jwdeveloper.ff.core.files.yaml.api.models.YamlModel;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class SimpleYamlModelFactory implements YamlModelFactory {
    @Override
    public <T> YamlContent createModel(Class<T> clazz) throws ClassNotFoundException
    {
        return createModel(clazz, StringUtils.EMPTY);
    }

    @Override
    public <T> YamlContent createModel(Class<T> clazz, String ymlPath) throws ClassNotFoundException {
        try
        {
            var root = getYamlContentModel(clazz);
            if(StringUtils.isNotNullOrEmpty(ymlPath))
            {
                root.setPath(ymlPath);
            }
            createContent(root, clazz);

            root.setDescription(generateDescription(root.getChildren()));
            return root;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to create Yaml Model",e);
        }
    }

    private void createContent(YamlContent root, Class<?> clazz) throws ClassNotFoundException {
        var result = new ArrayList<YamlContent>();
        for (var field : clazz.getDeclaredFields())
        {
            if (!field.isAnnotationPresent(YamlSection.class)) {
                continue;
            }
            var contentModel = getYamlContentModel(field);
            contentModel.setPath(root.getFullPath());
            if (field.getType().isAssignableFrom(List.class))
            {
                var arrayType = (ParameterizedType) field.getGenericType();
                var memberType = arrayType.getActualTypeArguments()[0];
                var memberClass = Class.forName(memberType.getTypeName());
                createContent(contentModel, memberClass);
                contentModel.setList(true);
            } else
            {
                createContent(contentModel, field.getType());
            }
            result.add(contentModel);
        }
        root.setChildren(result);
    }

    private YamlContent getYamlContentModel(Class<?> clazz) {
        var result = new YamlContent();
        result.setClazz(clazz);
        if (!clazz.isAnnotationPresent(YamlSection.class)) {
            return result;
        }

        var annotation = clazz.getAnnotation(YamlSection.class);
        result.setDescription(annotation.description());
        if (!annotation.name().isEmpty())
        {
            result.setName(annotation.name());
        }
        if (!annotation.path().isEmpty()) {
            result.setPath(annotation.path());
        }
        return result;
    }

    private YamlContent getYamlContentModel(Field field) {
        var result = new YamlContent();
        result.setClazz(field.getType());
        result.setField(field);
        result.setName(field.getName());
        if (!field.isAnnotationPresent(YamlSection.class)) {
            return result;
        }

        var annotation = field.getAnnotation(YamlSection.class);
        result.setDescription(annotation.description());
        if (!annotation.name().isEmpty())
        {
            result.setName(annotation.name());
        }
        if (!annotation.path().isEmpty()) {
            result.setPath(annotation.path());
        }
        return result;
    }

    private String generateDescription(List<YamlContent> contents) {
        if (contents.isEmpty())
            return StringUtils.EMPTY;

        var builder = new TextBuilder();
        for (var ymlContent : contents) {
            var parentPath = ymlContent.getFullPath();

            if (StringUtils.isNotNullOrEmpty(ymlContent.getDescription()) && !ymlContent.getDescription().equals(YamlSymbols.SPACE)) {
                builder.newLine().text(parentPath).newLine()
                        .space().text(ymlContent.getDescription()).newLine().newLine();
            }

            for (var child : ymlContent.getChildren()) {
                var description = child.getDescription();
                if (description.isEmpty() || StringUtils.isNullOrEmpty(description) || description.equals(YamlSymbols.SPACE)) {
                    continue;
                }
                builder.newLine().text(parentPath).text(YamlSymbols.DOT).text(child.getFullPath()).newLine()
                        .space().text(description).newLine();
            }
            generateDescription(ymlContent.getChildren());
        }

        return builder.toString();
    }
}

package io.github.jwdeveloper.ff.core.files.yaml.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.files.yaml.api.models.YamlContent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class SimpleYamlValueResolver {
    private Object getFieldValue(Object object, YamlContent content) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var field = content.getField();
        field.setAccessible(true);
        Object value = field.get(object);
        if (value == null) {
            return getDefaultValue(content.getClazz());
        }
        field.setAccessible(false);
        if (value.getClass().isEnum()) {
            var method = value.getClass().getMethod("name");
            return method.invoke(value);
        }
        if (field.getType().getName().equalsIgnoreCase("double")) {
            value = Double.parseDouble(value.toString());
        }
        if (field.getType().getName().equalsIgnoreCase("float")) {
            value = Float.parseFloat(value.toString());
        }
        return value;
    }

    private Object getDefaultValue(Class<?> type) {
        if (type.equals(String.class)) {
            return StringUtils.EMPTY;
        }
        if (type.equals(Integer.class)) {
            return 0;
        }
        if (type.equals(Float.class)) {
            return 0;
        }
        if (type.equals(Double.class)) {
            return 0;
        }
        if (type.equals(Boolean.class)) {
            return false;
        }
        if (type.equals(Material.class)) {
            return Material.DIRT;
        }
        if (type.equals(ChatColor.class)) {
            return ChatColor.WHITE;
        }
        if (type.isEnum()) {
            var enums = type.getEnumConstants();
            if (enums.length != 0) {
                return enums[0];
            }
        }

        return StringUtils.EMPTY;
    }

    private boolean isPrimitiveClass(Class<?> type) {
        if (type.isEnum()) {
            return true;
        }

        var primitiveClasses = new ArrayList<Class<?>>();
        primitiveClasses.add(ChatColor.class);
        primitiveClasses.add(Material.class);
        primitiveClasses.add(Boolean.class);
        primitiveClasses.add(Float.class);
        primitiveClasses.add(Integer.class);
        primitiveClasses.add(String.class);
        return primitiveClasses.contains(type);
    }

    public <T> void setValue(T data, YamlConfiguration configuration, YamlContent content, boolean override) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = getFieldValue(data, content);
        if (configuration.contains(content.getFullPath()) && !override) {
            return;
        }
        configuration.set(content.getFullPath(), value);
    }


    public ConfigurationSection setObject(Object object, YamlConfiguration configuration, YamlContent content, boolean overrite) {
        ConfigurationSection section = null;
        if (configuration.isConfigurationSection(content.getFullPath())) {
            section = configuration.getConfigurationSection(content.getFullPath());
        } else {
            section = configuration.createSection(content.getFullPath());
        }
        try {
            content.getField().setAccessible(true);
            var instance = content.getField().get(object);
            if (instance == null) {
                instance = content.getClazz().newInstance();
            }

            for (var child : content.getChildren()) {
                var value = getFieldValue(instance, child);
                if (section.contains(child.getFullPath()) && !overrite) {
                    continue;
                }
                if(isPrimitiveClass(value.getClass()))
                {
                    configuration.set(child.getFullPath(), value);
                    continue;
                }
                setObject(instance, configuration, child, overrite);
            }
            content.getField().setAccessible(false);
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Setting nested object error", e);
        }
        return section;
    }

    public ConfigurationSection setListContent(Object object, YamlConfiguration configuration, YamlContent content) {

        ConfigurationSection section = null;
        if (configuration.isConfigurationSection(content.getFullPath())) {
            section = configuration.getConfigurationSection(content.getFullPath());
        } else {
            section = configuration.createSection(content.getFullPath());
        }
        try {
            content.getField().setAccessible(true);
            var objects = (List<?>) content.getField().get(object);
            var i = 1;
            for (var obj : objects) {
                var childPath = "value-" + i;
                var childSection = section.createSection(childPath);
                for (var child : content.getChildren()) {
                    var value = getFieldValue(obj, child);
                    childSection.set(child.getName(), value);
                }
                i++;
            }
            content.getField().setAccessible(false);
        } catch (Exception e) {
            FluentLogger.LOGGER.error("List mapping error", e);
        }
        return section;
    }


    public Object getValue(ConfigurationSection section, YamlContent content) throws Exception {

        var path = content.getFullPath();
        var value = section.get(path);
        if(value instanceof ConfigurationSection)
        {
            return getObject(section, content);
        }
        if (value == null)
        {
            return getDefaultValue(content.getClazz());
        }

        if (content.getClazz().isEnum()) {
            return Enum.valueOf((Class<? extends Enum>) content.getClazz(), (String) value);
        }


        return value;
    }


    public Object getObject(ConfigurationSection rootSection, YamlContent content) throws Exception {
        var path = content.getFullPath();
        var instance = content.getClazz().newInstance();
        if (!rootSection.isConfigurationSection(path)) {
            return instance;
        }
        try {
            for (var child : content.getChildren()) {
                var value = getValue(rootSection, child);
                var field = child.getField();
                field.setAccessible(true);
                field.set(instance, value);
                field.setAccessible(false);
            }

        } catch (Exception e) {
            FluentLogger.LOGGER.error("Nested object mapping error", e);
        }
        return instance;
    }


    public Object getListContent(ConfigurationSection rootConfig, YamlContent content) {
        List<?> result = new ArrayList<>();
        try {
            var listPath = content.getFullPath();
            if (!rootConfig.isConfigurationSection(listPath)) {
                return result;
            }
            var listSection = rootConfig.getConfigurationSection(listPath);
            var listField = content.getField();
            var arrayType = (ParameterizedType) listField.getGenericType();
            var memberType = arrayType.getActualTypeArguments()[0];
            var memberClass = Class.forName(memberType.getTypeName());


            var sectionKeys = listSection.getKeys(false);
            var methodAdd = result.getClass().getDeclaredMethod("add", Object.class);
            for (var path : sectionKeys) {
                var temp = memberClass.newInstance();
                var propertiesPath = rootConfig.getConfigurationSection(listPath + "." + path).getKeys(false);
                for (var childContent : content.getChildren()) {
                    if (!propertiesPath.contains(childContent.getName())) {
                        continue;
                    }
                    var contentClone = childContent.clone();
                    contentClone.setPath(listPath + "." + path);

                    var value = getValue(rootConfig, contentClone);
                    var field = childContent.getField();

                    field.setAccessible(true);
                    field.set(temp, value);
                    field.setAccessible(false);
                }
                methodAdd.invoke(result, temp);
            }
            return result;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Could not load list configuration", e);
        }
        return result;
    }
}

package io.github.jwdeveloper.ff.core.files.yaml.api.models;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
public class YamlContent {
    private List<YamlContent> children = new ArrayList<>();
    private String name = StringUtils.EMPTY;
    private String path = StringUtils.EMPTY;

    private String description = StringUtils.EMPTY;

    private Field field;

    private Class<?> clazz;

    private boolean isList;

    public boolean hasDescription()
    {
        return description != null && !description.equals(StringUtils.EMPTY);
    }
    public boolean isObject() {
        return children.size() != 0;
    }

    public String getFullPath() {
        if (path.isEmpty()) {
            return name;
        }
        if(name.isEmpty())
        {
            return path;
        }
        return path + "." + name;
    }

    @Override
    public YamlContent clone()
    {
        var temp = new YamlContent();
        temp.setPath(path);
        temp.setName(name);
        temp.setField(field);
        temp.setChildren(children);
        temp.setClazz(clazz);
        temp.setDescription(description);
        temp.setList(isList);
        return temp;
    }
}

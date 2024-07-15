package io.github.jwdeveloper.ff.animations.impl.data.custom;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class NodeData {
    private String name;
    private List<NodeValue> values = new ArrayList<>();
    private Map<String, String> byName = new HashMap<>();

    public void addValue(NodeValue value) {
        this.byName.put(value.getName(), value.getValue());
        this.values.add(value);
    }

    public String getOrDefault(String key, String defaultValue) {
        if (!byName.containsKey(key)) {
            return defaultValue;
        }
        return byName.get(key);
    }

    public float getFloat(String key) {
        var value = getOrDefault(key, "0");
        return Float.parseFloat(value);
    }
}

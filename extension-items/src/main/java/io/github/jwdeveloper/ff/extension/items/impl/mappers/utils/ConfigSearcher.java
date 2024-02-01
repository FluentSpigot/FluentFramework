package io.github.jwdeveloper.ff.extension.items.impl.mappers.utils;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConfigSearcher {
    private final ConfigurationSection section;
    private final Set<String> searchedKey = new HashSet<>();

    public ConfigSearcher(ConfigurationSection section) {
        this.section = section;
    }


    /**
     * @param defaultValue
     * @param oneOfKey     set of config keys that might contains value
     * @param <T>          retrun value
     * @return
     */
    public <T> void findOrDefault(Consumer<T> onFind, T defaultValue, String... oneOfKey) {
        for (var key : oneOfKey) {
            searchedKey.add(key);
            if (!section.isSet(key)) {
                continue;
            }
            var value = section.get(key);
            var val = (T) value;
            onFind.accept(val);
            return;
        }
        onFind.accept(defaultValue);
    }


    /**
     * @return proprerties that was not searched before
     */
    public Map<String, Object> getNotSearchedValues() {
        return section.getKeys(false)
                .stream()
                .filter(e -> !searchedKey.contains(e))
                .collect(Collectors.toMap(key -> key, section::get));

    }
}

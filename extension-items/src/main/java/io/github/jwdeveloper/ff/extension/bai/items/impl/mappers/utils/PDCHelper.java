package io.github.jwdeveloper.ff.extension.bai.items.impl.mappers.utils;

import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.Consumer;

public class PDCHelper {

    private final PersistentDataContainer container;

    @Setter
    private boolean throwIfNotFound = false;

    public PDCHelper(PersistentDataContainer container) {
        this.container = container;
    }

    public void find(NamespacedKey namespacedKey, Consumer<String> onFind) {
        if (!container.has(namespacedKey, PersistentDataType.STRING)) {
            if (!throwIfNotFound) {
                return;
            }
            throw new RuntimeException("Unable to find value of: " + namespacedKey.getKey() + " in the item PDC");
        }
        var value = container.get(namespacedKey, PersistentDataType.STRING);
        onFind.accept(value);
    }


    public void set(NamespacedKey namespacedKey, Object value) {
        container.set(namespacedKey, PersistentDataType.STRING, value.toString());
    }

    public void setIfNotExists(NamespacedKey namespacedKey, Object value) {
        throw new RuntimeException("not implementated");
    }
}

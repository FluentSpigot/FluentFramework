package resources.migration;

import io.github.jwdeveloper.ff.plugin.api.config.migrations.ExtensionMigration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class Migration_3_0_0 implements ExtensionMigration {
    @Override
    public String version() {
        return "3.0.0";
    }

    @Override
    public void onUpdate(YamlConfiguration config) throws IOException {
        config.set("example",null);
        config.set("new.example","hello world");
    }
}

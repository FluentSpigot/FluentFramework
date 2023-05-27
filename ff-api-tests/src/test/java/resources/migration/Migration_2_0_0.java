package resources.migration;

import io.github.jwdeveloper.ff.plugin.api.config.migrations.ExtensionMigration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;


public class Migration_2_0_0 implements ExtensionMigration {
    @Override
    public String version() {
        return "2.0.0";
    }

    @Override
    public void onUpdate(YamlConfiguration config) throws IOException {
        config.set("example","hello world");
    }
}

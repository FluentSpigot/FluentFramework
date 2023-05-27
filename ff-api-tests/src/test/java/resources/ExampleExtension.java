package resources;

import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.config.migrations.ExtensionMigration;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import resources.migration.Migration_2_0_0;
import resources.migration.Migration_3_0_0;

import java.util.List;

public class ExampleExtension implements FluentApiExtension
{
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public String getVersion() {
        return "3.0.0";
    }


    @Override
    public List<ExtensionMigration> getMigrations() {
        return List.of(new Migration_3_0_0(), new Migration_2_0_0());
    }
}

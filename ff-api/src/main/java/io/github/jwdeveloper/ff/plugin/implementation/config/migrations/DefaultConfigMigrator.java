package io.github.jwdeveloper.ff.plugin.implementation.config.migrations;

import io.github.jwdeveloper.ff.core.common.java.ObjectUtility;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.core.common.versions.VersionCompare;
import io.github.jwdeveloper.ff.core.common.versions.VersionNumberComparator;
import io.github.jwdeveloper.ff.plugin.api.assembly_scanner.JarScanner;
import io.github.jwdeveloper.ff.plugin.api.config.migrations.ExtensionMigration;
import io.github.jwdeveloper.ff.plugin.api.config.migrations.ConfigMigrator;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultConfigMigrator implements ConfigMigrator {
    private final FluentApiExtension extension;
    private final JarScanner jarScanner;
    private final PluginLogger logger;

    public DefaultConfigMigrator(FluentApiExtension fluentApiExtension,
                                 JarScanner jarScanner,
                                 PluginLogger logger) {
        this.extension = fluentApiExtension;
        this.jarScanner = jarScanner;
        this.logger = logger;
    }

    @Override
    public boolean isConfigUpdated(YamlConfiguration configuration) {
        if (Objects.equals(extension.getVersion(), StringUtils.EMPTY)) {
            return false;
        }

        var configVersion = getConfigVersion(configuration);
        if (configVersion == null) {
            setConfigVersion(configuration, extension.getVersion());
            return false;
        }

        return VersionCompare.isHigher(extension.getVersion(), configVersion);
    }

    @Override
    public void makeMigration(YamlConfiguration configuration) {

        if(StringUtils.isNullOrEmpty(extension.getVersion()))
        {
            return;
        }

        var packageName = extension.getClass().getPackageName();
        final List<ExtensionMigration> migrations = new ArrayList(extension.getMigrations());
        jarScanner.findByInterface(ExtensionMigration.class)
                .stream()
                .filter(e ->  e.getPackageName().contains(packageName))
                .toList()
                .forEach(migrationClazz ->
                {
                    try {
                        migrations.add(ObjectUtility.initialize(migrationClazz));
                    } catch (Exception e) {
                        throw new RuntimeException("Unable to load migrations", e);
                    }
                });

        var currentVersion = extension.getVersion();
        var configVersion = getConfigVersion(configuration);

        if(currentVersion.equals(configVersion))
        {
            return;
        }

        if(migrations.size() == 0|| configVersion == null)
        {
            setConfigVersion(configuration, currentVersion);
            return;
        }


        var sorted = getMigrationsBetween(migrations, configVersion, currentVersion);
        logger.info("Migration from",configVersion,"to",currentVersion,"has started");
        for(var migration : sorted)
        {
            logger.info("Migrating config to plugin version",migration.version());
            try {
                migration.onUpdate(configuration);
            }
            catch (Exception e)
            {
                logger.warning("Error while migration to"+migration.version(),e.getMessage());
            }

        }

        setConfigVersion(configuration, currentVersion);
    }

    private String getConfigVersion(YamlConfiguration configuration) {
        var versionPath = "versions." + extension.getName();
        return configuration.getString(versionPath);
    }

    private void setConfigVersion(YamlConfiguration configuration, String version) {
        var versionPath = "versions." + extension.getName();
        configuration.set(versionPath, version);
    }

    private List<ExtensionMigration> sortByVersion(List<ExtensionMigration> migrations) {
        var result = new ArrayList<ExtensionMigration>();
        var versionNames = new ArrayList<>(migrations.stream().map(ExtensionMigration::version).toList());
        versionNames.sort(VersionNumberComparator.getInstance());

        for (var version : versionNames) {
            var optional = migrations.stream().filter(c -> c.version().equals(version)).findFirst();
            if (optional.isEmpty()) {
                logger.warning("Problem with sort migration for version", version);
                continue;
            }
            result.add(optional.get());
        }
        return result;
    }

    private List<ExtensionMigration> getMigrationsBetween(List<ExtensionMigration> migrations, String configVersion, String currentVersion) {
        var sorted = sortByVersion(migrations);
        var result = new ArrayList<ExtensionMigration>();
        for (var migration : sorted) {
            var equalCurrent = currentVersion.equals(migration.version());

            var isLowerThenCurrent = VersionCompare.isLower(migration.version(), currentVersion);
            var isLowerThenOld = VersionCompare.isLower(configVersion, migration.version());
            if ((equalCurrent || isLowerThenCurrent) && isLowerThenOld) {
                result.add(migration);
            }
        }
        return result;
    }

}

package io.github.jwdeveloper.ff.plugin.addons;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.assemby_scanner.JarScannerImpl;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class AddonsExtension implements FluentApiExtension {

    private final Consumer<AddonsOptions> optionsConsumer;

    public AddonsExtension(Consumer<AddonsOptions> optionsConsumer) {
        this.optionsConsumer = optionsConsumer;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        var options = new AddonsOptions();
        optionsConsumer.accept(options);

        var addonsPath = Paths.get(builder.pluginPath().toString(), options.getAddonsPath());
        FileUtility.ensurePath(addonsPath.toString());

        var addonsFiles = FileUtility.getAllFiles(addonsPath.toAbsolutePath().toString(), "jar");
        var extensions = new HashMap<String, List<FluentApiExtension>>();
        for (var addonPath : addonsFiles) {
            try {
                var classLoader = new AddonsClassLoader(this.getClass().getClassLoader(), addonPath);
                var loadedClasses = classLoader.loadAll();
                var addonExtensions = getFluentApiExtensions(addonPath, new JarScannerImpl(loadedClasses, builder.logger()));
                extensions.put(addonPath, addonExtensions);
                builder.jarScanner().addClasses(loadedClasses);
            } catch (Exception e) {
                throw new RuntimeException("Unable to load addon " + addonsFiles, e);
            }
        }

        for (var entry : extensions.entrySet()) {
            for (var extension : entry.getValue()) {
                builder.useExtension(extension);
            }
            options.getOnAddonLoaded().accept(entry.getKey());
        }
    }

    private List<FluentApiExtension> getFluentApiExtensions(String path, JarScannerImpl classes) throws Exception {
        var extensionsClasses = classes.findByInterface(FluentApiExtension.class);
        var result = new ArrayList<FluentApiExtension>();
        for (var clazz : extensionsClasses) {
            var instance = (FluentApiExtension) clazz.getConstructors()[0].newInstance();
            result.add(instance);
        }
        return result;
    }

    @Override
    public ExtentionPriority getPriority() {
        return ExtentionPriority.LOW;
    }
}

package io.github.jwdeveloper.ff.plugin.implementation.assemby_scanner;

import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestsJarScanner extends JarScannerImpl {


    public TestsJarScanner(Class<?> clazz, PluginLogger logger) {
        super(clazz, logger);
    }

    public TestsJarScanner(Plugin plugin, PluginLogger logger) {
        super(plugin, logger);
    }


    @Override
    protected List<Class<?>> loadClasses(Class<?> clazz) {
        var classessNames = findClasses(clazz.getPackageName());
        var output = new ArrayList<Class<?>>();
        for (var className : classessNames) {
            try {
                var classOutput = Class.forName(className, false, clazz.getClassLoader());
                output.add(classOutput);
            } catch (Exception e) {

            }
        }
        return output;
    }


    public static List<String> findClasses(String packageName) {
        List<String> classNames = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(path);

        if (resource == null) {
            return classNames;
        }

        File directory = new File(resource.getFile());
        findClasses(directory, packageName, classNames);
        return classNames;
    }

    private static void findClasses(File directory, String packageName, List<String> classNames) {
        if (!directory.exists()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    findClasses(file, packageName + "." + file.getName(), classNames);
                } else if (file.getName().endsWith(".class")) {
                    String className = file.getName().replace(".class", "");
                    String fullClassName = packageName + "." + className;
                    classNames.add(fullClassName);
                }
            }
        }
    }
}

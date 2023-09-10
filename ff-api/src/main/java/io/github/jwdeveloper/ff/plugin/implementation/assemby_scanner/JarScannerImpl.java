package io.github.jwdeveloper.ff.plugin.implementation.assemby_scanner;

import io.github.jwdeveloper.ff.core.common.java.ClassTypeUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.plugin.api.assembly_scanner.JarScanner;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class JarScannerImpl extends ClassLoader implements JarScanner {

    @Getter
    private final List<Class<?>> classes;

    private final Map<Class<?>, List<Class<?>>> byInterfaceCatch;

    private final Map<Class<?>, List<Class<?>>> byParentCatch;

    private final Map<Package, List<Class<?>>> byPackageCatch;

    private final Map<Class<? extends Annotation>, List<Class<?>>> byAnnotationCatch;

    private final PluginLogger logger;

    public JarScannerImpl(Plugin plugin, PluginLogger logger) {
        this(plugin.getClass(), logger);
    }

    public JarScannerImpl(Class<?> clazz, PluginLogger logger) {
        this.logger = logger;
        classes = loadClassess(clazz);
        byInterfaceCatch = new IdentityHashMap<>();
        byParentCatch = new IdentityHashMap<>();
        byPackageCatch = new IdentityHashMap<>();
        byAnnotationCatch = new HashMap<>();
    }


    private List<Class<?>> loadClassess(final Class<?> clazz) {
        final var source = clazz.getProtectionDomain().getCodeSource();
        if (source == null)
            return Collections.emptyList();
        final var url = source.getLocation();
        try (final var zip = new ZipInputStream(url.openStream())) {
            final List<Class<?>> classes = new ArrayList<>();
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory())
                    continue;
                var name = entry.getName();
                if (name.startsWith("META-INF"))
                    continue;
                if (!name.endsWith(".class"))
                    continue;
                name = name.replace('/', '.').substring(0, name.length() - 6);
                try {
                    classes.add(Class.forName(name, false, clazz.getClassLoader()));
                }
                catch (IncompatibleClassChangeError e)
                {

                }
                catch (NoClassDefFoundError | ClassNotFoundException e)
                {
                    logger.error("Unable to load class:" + name, e);
                }
            }
            return classes;
        } catch (IOException e) {
            logger.error("Unable open plugin Jar file :", e);
            return Collections.emptyList();
        }
    }

    public void attacheAllClassesFromPackage(Class<?> clazz)
    {
        classes.addAll(loadClassess(clazz));
        byInterfaceCatch.clear();;
        byAnnotationCatch.clear();
        byPackageCatch.clear();;
        byParentCatch.clear();;
    }


    public Collection<Class<?>> findByAnnotation(Class<? extends Annotation> annotation) {
        if (byAnnotationCatch.containsKey(annotation)) {
            return byAnnotationCatch.get(annotation);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            if (_class.isAnnotationPresent(annotation)) {
                result.add(_class);
            }
        }
        byAnnotationCatch.put(annotation, result);
        return result;
    }

    public Collection<Class<?>> findByInterface(Class<?> _interface) {
        if (byInterfaceCatch.containsKey(_interface)) {
            return byInterfaceCatch.get(_interface);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            for (var _classInterface : _class.getInterfaces()) {
                if (_classInterface.equals(_interface)) {
                    result.add(_class);
                    break;
                }
            }
        }
        byInterfaceCatch.put(_interface, result);
        return result;
    }

    public Collection<Class<?>> findBySuperClass(Class<?> superClass) {
        if (byParentCatch.containsKey(superClass)) {
            return byParentCatch.get(superClass);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            if (ClassTypeUtility.isClassContainsType(_class, superClass)) {
                result.add(_class);
            }
        }
        byParentCatch.put(superClass, result);
        return result;
    }

    public Collection<Class<?>> findByPackage(Package _package) {
        if (byPackageCatch.containsKey(_package)) {
            return byPackageCatch.get(_package);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            for (var _classInterface : _class.getInterfaces()) {
                if (_classInterface.getPackage().equals(_package)) {
                    result.add(_class);
                    break;
                }
            }
        }
        byPackageCatch.put(_package, result);
        return result;
    }


}

package io.github.jwdeveloper.ff.plugin.addons;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AddonsClassLoader extends ClassLoader {

    private String jarFilePath;

    public AddonsClassLoader(ClassLoader parent, String jarFilePath) {
        super(parent);
        this.jarFilePath = jarFilePath;
    }

    public Collection<Class<?>> loadAll() {
        try (JarFile jarFile = new JarFile(jarFilePath)) {
            var result = new ArrayList<Class<?>>();
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    String className = entry.getName().substring(0, entry.getName().length() - ".class".length()).replace('/', '.');
                    Class<?> loadedClass = this.loadClass(className);
                    result.add(loadedClass);
                }
            }
            return result;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            JarEntry jarEntry = jarFile.getJarEntry(className.replace('.', '/') + ".class");

            if (jarEntry != null) {
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int data;
                while ((data = inputStream.read()) != -1) {
                    byteArrayOutputStream.write(data);
                }
                inputStream.close();
                byte[] classBytes = byteArrayOutputStream.toByteArray();
                return defineClass(className, classBytes, 0, classBytes.length);
            } else {
                throw new ClassNotFoundException("Class not found: " + className);
            }
        } catch (IOException e) {
            throw new ClassNotFoundException("Error loading class: " + className, e);
        }
    }

}
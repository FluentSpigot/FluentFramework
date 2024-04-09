package io.github.jwdeveloper.ff.core.files;

import com.google.common.io.Files;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public interface FileUtility {
    static String serverPath() {
        return Paths.get("").toAbsolutePath().toString();
    }

    static String separator() {
        return File.separator;
    }

    static String pluginsPath() {
        return serverPath() + File.separator + "plugins";
    }

    static boolean isPathValid(String path) {
        if (path == null)
            return false;

        return new File(path).exists();
    }

    public static String getProjectPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 1);
        return path;
    }


    static String getProgramPath() {
        return System.getProperty("user.dir");
    }

    static String getPathFileName(String path) {
        return Paths.get(path).getFileName().toString();
    }

    static void saveClassFile(String result, String outputPath, String fileName) throws IOException {
        FileUtility.ensurePath(outputPath);
        outputPath = outputPath + FileUtility.separator() + fileName + ".java";
        var writer = new FileWriter(outputPath);
        writer.write(result);
        writer.close();
    }


    static void removeDirectory(File file) {
        if (file == null)
            return;

        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null) {
                for (File childFile : files) {
                    if (childFile.isDirectory())
                        removeDirectory(childFile);
                    else if (!childFile.delete()) {

                    }
                    FluentLogger.LOGGER.info("Could not delete path: " + childFile.getName());
                }
            }
        }

        if (!file.delete()) {

        }
        FluentLogger.LOGGER.info("Could not delete path: " + file.getName());
    }

    static String pluginPath(Plugin javaPlugin) {
        return javaPlugin.getDataFolder().getAbsoluteFile().toString();
    }


    static boolean downloadFile(String url, String outputPath) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
        return true;
    }

    static File pluginFile(JavaPlugin plugin) {
        try {
            var fileField = JavaPlugin.class.getDeclaredField("file");
            fileField.setAccessible(true);
            return (File) fileField.get(plugin);
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Can not load plugin path", e);
            return null;
        }
    }

    static String combinePath(String... paths) {
        var res = "";
        for (var p : paths) {
            res += p + File.separator;
        }
        return res;
    }

    static boolean save(String content, String path, String fileName) {
        return save(content, path + File.separator + fileName);
    }

    static boolean save(String content, String path) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(content);
            return true;
        } catch (IOException e) {
            FluentLogger.LOGGER.error("Save File: " + path, e);
        }
        return false;
    }

    static List<String> getAllFiles(String path, String... extensions) {
        final var result = new ArrayList<String>();
        if (!isPathValid(path)) {
            FluentLogger.LOGGER.info("Files count not be loaded since path " + path + " not exists!");
            return result;
        }
        final File folder = new File(path);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                var files = getAllFiles(fileEntry.getPath(), extensions);
                result.addAll(files);
                continue;
            }
            if (extensions.length == 0) {
                result.add(fileEntry.getPath());
                continue;
            }

            var name = fileEntry.getName();
            var dotIndex = name.lastIndexOf('.');
            var extension = name.substring(dotIndex + 1);
            for (var fileName : extensions) {
                if (extension.equalsIgnoreCase(fileName.toLowerCase()))
                {
                    result.add(fileEntry.getPath());
                    break;
                }
            }

        }
        return result;
    }

    static List<String> getFolderFilesName(String path, String... extensions) {
        final ArrayList<String> filesName = new ArrayList<>();
        if (!isPathValid(path)) {
            FluentLogger.LOGGER.info("Files count not be loaded since path " + path + " not exists!");
            return filesName;
        }
        final File folder = new File(path);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                // recursion Get_FileNames
            } else {
                if (extensions.length == 0) {
                    filesName.add(fileEntry.getName());
                    continue;
                }

                String name = fileEntry.getName();
                int dotIndex = name.lastIndexOf('.');
                String extension = name.substring(dotIndex + 1);
                for (String fileName : extensions) {
                    if (extension.equalsIgnoreCase(fileName.toLowerCase())) {
                        filesName.add(fileEntry.getName());
                        break;
                    }
                }
            }
        }
        return filesName;
    }


    static boolean pathExists(String path) {
        var directory = new File(path);
        return directory.exists();
    }

    static File ensurePath(String path) {
        var directory = new File(path);
        if (directory.exists()) {
            return directory;
        }
        directory.mkdirs();
        return directory;
    }

    static void ensureFile(String paths) {
        var file = new File(paths);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                FluentLogger.LOGGER.error("Could not ensure file: " + paths, e);
            }
        }
    }
    public static YamlConfiguration loadFileFromResponse(String fileName, Class<?> clazz) {
        var classLoader = clazz.getClassLoader();
        try (var inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new RuntimeException("Default config has not been found!");
            }

            try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return YamlConfiguration.loadConfiguration(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load default config file", e);
        }
    }

    static String loadFileContent(String path) throws IOException {
        ensureFile(path);

        StringBuilder contentBuilder = new StringBuilder();
        try (var scanner = new Scanner(new File(path), StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                contentBuilder.append(scanner.nextLine()).append(System.lineSeparator());
            }
        }
        return contentBuilder.toString();
    }

    static String loadInputStream(InputStream inputStream) {
        var stringBuilder = new StringBuilder();
        try {

            String line;
            try (var bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append('\n');  // optional, for newline preservation
                }
            }

        } catch (Exception e) {
            stringBuilder.append("LOADING FILE ERROR").append(e.getMessage());
        }
        return stringBuilder.toString();
    }

    static String[] loadFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            FluentLogger.LOGGER.error("Could not loadFile", e);
        }
        return lines.toArray(new String[0]);
    }

    static void saveToFile(String path, String name, String content) throws IOException {
        ensurePath(path);
        Files.write(content.getBytes(), new File(Path.of(path, name).toString()));
    }

    public static void copyFile(Path sourcePath, Path destPath) throws IOException {
        java.nio.file.Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static List<String> findAllYmlFiles(File file) {
        List<String> result = new ArrayList<>();
        try (JarInputStream is = new JarInputStream(new FileInputStream(file))) {
            JarEntry entry;
            while ((entry = is.getNextJarEntry()) != null) {
                try {
                    String name = entry.getName();
                    if (!name.endsWith(".yml")) {
                        continue;
                    }
                    result.add(name);
                } catch (Exception ex) {
                    FluentLogger.LOGGER.error("Could not load class", ex);
                }

            }
        } catch (Exception ex) {
            FluentLogger.LOGGER.error("Could not load class", ex);
        }
        return result;
    }
}

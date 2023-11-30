package io.github.jwdeveloper.ff.tools.resourcepack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;

public class ResourcepackCopyFromProject
{

    public static void copy(Consumer<ResourcePackCopyOptions> consumer)
    {
        ResourcePackCopyOptions options = new ResourcePackCopyOptions();
        consumer.accept(options);
        copyDirectoryContent(options.getInputPath(), options.getOutputPath());
    }

    public static boolean removeDirectoryContent(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the directory exists
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path.");
            return false;
        }

        // Get list of files in the directory
        File[] files = directory.listFiles();

        // Delete all files and subdirectories within the directory
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    removeDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return true;
    }
    public static void removeDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        removeDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }


    public static boolean copyDirectoryContent(String sourceDirectoryPath, String destinationDirectoryPath) {
        File sourceDirectory = new File(sourceDirectoryPath);
        File destinationDirectory = new File(destinationDirectoryPath);

        // Check if both directories exist and source is a directory
        if (!sourceDirectory.exists() || !sourceDirectory.isDirectory() || !destinationDirectory.exists()) {
            System.out.println("Invalid source or destination directory path.");
            return false;
        }

        // Get list of files in the source directory
        File[] files = sourceDirectory.listFiles();

        // Copy each file/directory to the destination directory
        if (files != null) {
            for (File file : files) {
                File destination = new File(destinationDirectoryPath + File.separator + file.getName());
                try {
                    if (file.isDirectory()) {
                        copyDirectory(file, destination);
                    } else {
                        Files.copy(file.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    public static void copyDirectory(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }

            File[] files = source.listFiles();
            if (files != null) {
                for (File file : files) {
                    File newDestination = new File(destination.getAbsolutePath() + File.separator + file.getName());
                    if (file.isDirectory()) {
                        copyDirectory(file, newDestination);
                    } else {
                        Files.copy(file.toPath(), newDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
    }
}

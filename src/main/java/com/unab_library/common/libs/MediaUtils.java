package com.unab_library.common.libs;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MediaUtils {
    private MediaUtils() {
        // Private constructor to prevent instantiation
    }
    public static byte[] loadImage(String path) throws IOException {
        File file = new File(path);
        return Files.readAllBytes(file.toPath());
    }
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        Path path = Paths.get(fileName);
        String fileNameStr = path.getFileName().toString();
        int dotIndex = fileNameStr.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileNameStr.length() - 1) {
            return "";
        }
        return fileNameStr.substring(dotIndex + 1);
    }
    public static boolean isValidExtension(String extension, List<String> validExtensions) {
        return validExtensions.contains(extension);
    }
}

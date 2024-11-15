package com.unab_library.common.libs;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MediaUtils {
    public static final String RESOURCES_DIR = "src/main/resources/";
    private MediaUtils() {
        // Private constructor to prevent instantiation
    }
    public static byte[] loadImage(String path) throws IOException {

        Path imagePath = Paths.get(RESOURCES_DIR + path);
        try {
            if (imagePath == null) {
                throw new IllegalArgumentException("Image path cannot be null");
            }
            if (!Files.exists(imagePath)) {
                throw new IllegalArgumentException("Image file does not exist: " + imagePath);
            }
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file: " + imagePath, e);
        }
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

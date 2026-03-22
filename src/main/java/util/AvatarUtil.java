package util;

import java.io.File;
import javax.servlet.http.Part;

public class AvatarUtil {

    public static String getNextAvatarFileName(String uploadDir, String extension) {
        File folder = new File(uploadDir);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        int max = 0;

        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                String name = f.getName();

                int dotIndex = name.lastIndexOf(".");
                if (dotIndex > 0) {
                    String numberPart = name.substring(0, dotIndex);

                    try {
                        int num = Integer.parseInt(numberPart);
                        if (num > max) {
                            max = num;
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

        int next = max + 1;

        return next + "." + extension;
    }

    public static boolean isAvatarFileAllowed(String ext) {
        return ext.equals("jpg")
                || ext.equals("jpeg")
                || ext.equals("png")
                || ext.equals("gif")
                || ext.equals("webp");
    }

    public static String getFileExtension(Part part) {
        if (part == null) {
            return null;
        }

        String fileName = part.getSubmittedFileName();
        if (fileName == null || fileName.trim().isEmpty()) {
            return null;
        }

        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return null;
        }

        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    public static void deleteAvatarFile(String uploadDir, String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return;
        }

        File file = new File(uploadDir, fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }
}
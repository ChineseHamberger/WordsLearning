package tools;

import java.io.File;

public class FileKit {
    private FileKit() {};
    public static void clearAllFiles(File dir){
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        File[] subFiles = file.listFiles();
                        if (subFiles != null) {
                            for (File subFile : subFiles) {
                                try {
                                    if (!subFile.delete()) {
                                        System.err.println("无法删除文件：" + subFile.getAbsolutePath());
                                    }
                                } catch (SecurityException e) {
                                    System.err.println("权限不足，无法删除文件：" + subFile.getAbsolutePath());
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        try {
                            if (!file.delete()) {
                                System.err.println("无法删除文件：" + file.getAbsolutePath());
                            }
                        } catch (SecurityException e) {
                            System.err.println("权限不足，无法删除文件：" + file.getAbsolutePath());
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("wordBooks 目录为空");
            }
        } else {
            System.out.println("wordBooks 不是有效的目录");
        }
    }

}

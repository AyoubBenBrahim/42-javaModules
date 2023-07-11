package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Program {
    private static File currentFolder;

    public static void main(String[] args) {
        if (args.length > 0) {
            String path = null;
            // System.out.println(args[0]);
            if (args[0].contains("--current-folder=/Users")) {
                path = args[0].substring(args[0].indexOf('=') + 1);
                // System.out.println(path);
            } else {
                System.out.println("Error: The argument does not contain the --current-folder option.");
            }
            path = path.trim();
            // path = "." + path;
            currentFolder = new File(path);
            // changeDirectory(path);
        } else {
            System.out.println("Error: Please specify the absolute path to the folder where we start to work.");
            return;
        }

        if (!currentFolder.exists()) {
            System.out.println("Error: Folder does not exist.");
            return;
        }

        if (!currentFolder.isDirectory()) {
            System.out.println("Error: Path is not a directory.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println(currentFolder.getAbsolutePath());
            command = scanner.nextLine();
            String[] tokens = command.split(" ");
            tokens[0] = tokens[0].toLowerCase();
            tokens[0] = tokens[0].trim();

            switch (tokens[0]) {

                case "ls":
                    if (tokens.length > 1) {
                        listFiles(tokens[1]);
                    } else {
                        listFiles(null);
                    }
                    break;
                case "cd":
                    if (tokens.length > 1)
                        changeDirectory(tokens[1]);
                    break;
                case "mv":
                    if (tokens.length > 2)
                        moveFile(tokens[1], tokens[2]);
                    break;
                case "pwd":
                    currentDirectory();
                    break;
                case "exit":
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid CMD");
            }
        }
    }

    private static void listFiles(String path) {
        File folder = path != null ? new File(currentFolder, path) : currentFolder;
        if (folder.exists() && folder.isDirectory()) {
            if (folder.canRead()) {
                File[] files = folder.listFiles();
                for (File file : files) {
                    String name = file.getName();
                    if (file.isDirectory()) {
                        name += "/";
                    }
                    String sizeStr;
                    float size = file.length();
                    if (size < 1024) {
                        sizeStr = size + " B";
                    } else if (size < 1024 * 1024) {
                        sizeStr = String.format("%.1f KB", size / 1024);
                    } else if (size < 1024 * 1024 * 1024) {
                        sizeStr = String.format("%.1f MB", size / (1024 * 1024));
                    } else {
                        sizeStr = String.format("%.1f GB", size / (1024 * 1024 * 1024));
                    }
                    System.out.println(name + " " + sizeStr.replaceAll("\\.0 ", " "));
                }
            } else {
                System.out.println("No read permission on the directory");
            }
        } else {
            System.out.println("Directory does not exist");
        }
    }

    private static void changeDirectory(String path) {
        File newFolder = new File(currentFolder, path);

        if (newFolder.exists() && newFolder.isDirectory()) {
            if (newFolder.canExecute())
                currentFolder = newFolder;
            else {
                System.out.println("No Permissions");
                return;
            }
            currentFolder = newFolder;
        } else {
            System.out.println("Directory does not exist");
        }
    }

    private static void currentDirectory() {
        System.out.println(currentFolder.getAbsolutePath());
    }

    private static void moveFile(String sourcePath, String destPath) {
        File sourceFile = new File(currentFolder, sourcePath);
        File destFile = new File(currentFolder, destPath);
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (destFile.exists() && destFile.isDirectory()) {
                File newDestFile = new File(destFile, sourceFile.getName());
                try {
                    Path sourcePathObj = Paths.get(sourceFile.getAbsolutePath());
                    Path destPathObj = Paths.get(newDestFile.getAbsolutePath());
                    Files.copy(sourcePathObj, destPathObj);
                    // System.out.println("File copied successfully");
                    Files.delete(sourcePathObj);

                } catch (IOException e) {
                    System.out.println("Error copying file: " + e.getMessage());
                }
            } else {
                boolean success = sourceFile.renameTo(destFile);
                if (success) {
                    // System.out.println("File renamed successfully");
                } else {
                    System.out.println("Error renaming file");
                }
            }
        } else {
            System.out.println("Source file does not exist");
        }
    }
}
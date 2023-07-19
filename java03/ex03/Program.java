package ex03;


import java.io.*;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Program {

    private static void printUsage() {
        System.err.println("Usage: ./compile.sh --threadsCount=1");
        System.exit(1);
    }

    private static final String FILE_URLS = "./files_urls.txt";
    private static final String DOWNLOAD_DIR = "./downloads/";
    private static int threadCount = 0;

    public static void main(String[] args) {

        if (args.length != 1 || !args[0].contains("--threadsCount="))
            printUsage();

        try {
            threadCount = Integer.parseInt(args[0].split("=")[1]);
            if (threadCount < 1)
                printUsage();
        } catch (Exception e) {
            printUsage();
        }

        File dir = new File(DOWNLOAD_DIR);
        if (dir.exists()) {
            for (File file : dir.listFiles())
                file.delete();
        } else
            dir.mkdir();

        List<String> urlsList = readUrlsFromFile(FILE_URLS);
        Queue<String> filesToDownload = new ArrayDeque<>(urlsList);

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new DownloadThread(urlsList, filesToDownload, DOWNLOAD_DIR));
            thread.start();
        }
    }

    public static boolean isURL(String url) {
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static List<String> readUrlsFromFile(String fileName) {
        List<String> urls = new ArrayList<>();

        File file = new File(fileName);
        if (!file.isFile() || !file.canRead()) {
            System.err.println("cannot read file: " + fileName);
            System.exit(-1);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty())
                    continue;
                if (isURL(line))
                    urls.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (urls.size() == 0) {
            System.err.println("File is empty");
            System.exit(-1);
        }
        return urls;
    }

    private static String getFileNameFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    private static void downloadThisShit(String fileUrl, String downloadDir, String fileName) {
        if (fileUrl == null)
            return;
        InputStream inputStream;
        try {
            inputStream = URI.create(fileUrl).toURL().openStream();
            Files.copy(inputStream, Paths.get(downloadDir, fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class DownloadThread implements Runnable {
        private final Queue<String> filesToDownload;
        private final String downloadDir;
        List<String> lst;

        public DownloadThread(List<String> lst, Queue<String> filesToDownload, String downloadDir) {
            this.filesToDownload = filesToDownload;
            this.downloadDir = downloadDir;
            this.lst = lst;
        }

        @Override
        public void run() {
            while (!filesToDownload.isEmpty()) {
                // String fileUrl = filesToDownload.remove();
                // if (fileUrl == null)
                // return; dont use Queue.remove() because it throws NoSuchElementException
                // remove method of the Queue interface does not return null when the queue is
                // empty.
                // Instead, it throws a NoSuchElementException
                // use poll() instead
                String fileUrl = filesToDownload.poll();
                if (fileUrl == null)
                    return;
                String fileName = getFileNameFromUrl(fileUrl);
                try {
                    Integer threadID = Integer.parseInt(Thread.currentThread().getName().split("-")[1]);// pool-3-thread-1
                    int fileID = lst.indexOf(fileUrl) + 1;
                    System.out.println("Thread-" + (threadID + 1) + " start download file " + fileID);
                    downloadThisShit(fileUrl, downloadDir, fileName);
                    System.out.println("Thread-" + (threadID + 1) + " finish download file " + fileID);
                    fileID++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
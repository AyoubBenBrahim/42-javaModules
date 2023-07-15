package ex03;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Program1 {
    private static final int MAX_THREADS = 15;
    private static final String FILE_URLS = "./files_urls.txt";
    private static final String DOWNLOAD_DIR = "./downloads/";

    public static void main(String[] args) {
        List<String> urlsList = readUrlsFromFile(FILE_URLS);
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        Semaphore semaphore = new Semaphore(MAX_THREADS);

        for (String url : urlsList) {
            try {
                semaphore.acquire();
                executor.execute(() -> {
                    downloadFile(url);
                    semaphore.release();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All files downloaded successfully.");
    }

    private static List<String> readUrlsFromFile(String fileName) {
        List<String> urls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                urls.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }

    private static void downloadFile(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String fileName = getFileNameFromUrl(url);
            System.out.println("Downloading " + fileName + " from " + fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                 FileOutputStream out = new FileOutputStream(fileName)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            System.out.println(fileName + " downloaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileNameFromUrl(URL url) {
        String[] segments = url.getPath().split("/");
        return segments[segments.length - 1];
    }
}

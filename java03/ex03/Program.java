package ex03;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;

public class Program {

    private static void printUsage() {
        System.err.println("Usage: ./compile.sh --threadsCount=4");
        System.exit(1);
    }

    private static final String FILE_URLS = "./files_urls.txt";
    private static final String DOWNLOAD_DIR = "./downloads/";

    private static final int BUFFER_SIZE = 4096;
    private static final int MAX_THREADS = 100;
    private static int threadCount = 0;

    public static void main(String[] args) {

        if (args.length != 1 || !args[0].contains("--threadsCount="))
            printUsage();

        try {
            threadCount = Integer.parseInt(args[0].split("=")[1]);
            if (threadCount < 1 || threadCount > MAX_THREADS)
                printUsage();
        } catch (Exception e) {
            printUsage();
        }

        File dir = new File(DOWNLOAD_DIR);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
        }

        List<String> urlsList = readUrlsFromFile(FILE_URLS);
        int fileNumber = 1;
        for (String fileUrl : urlsList) {
            String fileName = getFileNameFromUrl(fileUrl);
            downloadFile(fileUrl, fileName, fileNumber);
            fileNumber++;
        }
    }

    private static List<String> readUrlsFromFile(String fileName) {
        List<String> urls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty())
                    continue;
                urls.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }

    private static String getFileNameFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    private static void downloadFile(String fileUrl, String fileName, int fileNumber) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection;
            int timeout = 10000;
            long startTime = System.currentTimeMillis();

            do {
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(timeout);
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // System.out.println("Connection successful: " + fileUrl);
                    break;
                } else {
                    System.out.println("Connection failed (" + responseCode + "): " + fileUrl);
                    Thread.sleep(1000);
                }
                if (System.currentTimeMillis() - startTime > timeout) {
                    System.out.println("Timeout reached: " + fileUrl);
                    System.exit(1);
                }
            } while (true);

            long fileSize = connection.getContentLength();

            int chunkSize = (int) Math.ceil((double) fileSize / threadCount);

            ExecutorService executor = Executors.newFixedThreadPool(threadCount);

            for (int i = 0; i < threadCount; i++) {
                int startByte = i * chunkSize;
                int endByte = Math.min((i + 1) * chunkSize - 1, (int) fileSize - 1);
                executor.execute(new DownloadThread(fileUrl, startByte, endByte, DOWNLOAD_DIR, fileName));
                System.out.println("Thread-" + (i + 1) + " start download file number " + fileNumber);
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                Thread.sleep(1000);
            }

            System.out.println("Thread-1 finish download file number " + fileNumber);

            for (int i = 2; i <= threadCount; i++) {
                System.out.println("Thread-" + i + " finish download file number " + fileNumber);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            if (e.getMessage().equals("Network is unreachable (connect failed)")) {
                System.err.println("[ERROR] Cannot connect to server: network is unreachable");
                System.exit(1);
            } else {
                e.printStackTrace();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static class DownloadThread implements Runnable {
        private final String fileUrl;
        private final long startPos;
        private final long endPos;
        private final String downloadDir;
        private final String fileName;

        public DownloadThread(String fileUrl, long startPos, long endPos, String downloadDir, String fileName) {
            this.fileUrl = fileUrl;
            this.startPos = startPos;
            this.endPos = endPos;
            this.downloadDir = downloadDir;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set the range header
                String range = "bytes=" + startPos + "-" + endPos;
                connection.setRequestProperty("Range", range);

                // Open input stream and output file
                try (InputStream inputStream = connection.getInputStream();
                        RandomAccessFile outputFile = new RandomAccessFile(downloadDir + "/" + fileName, "rw")) {

                    // Seek to the correct position in the output file
                    outputFile.seek(startPos);

                    // Read from input stream and write to output file
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputFile.write(buffer, 0, bytesRead);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
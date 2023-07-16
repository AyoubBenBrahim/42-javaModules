package ex03;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;

public class HttpRangeRequests {

    private static void printUsage() {
        System.err.println("Usage: ./compile.sh --threadsCount=1");
        System.exit(1);
    }

    private static final String FILE_URLS = "./files_urls.txt";
    private static final String DOWNLOAD_DIR = "./downloads/";

    private static final int BUFFER_SIZE = 4096;
    private static final int CHUNK_SIZE = 1024 * 1024; // 1MB
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
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
        }

        List<String> urlsList = readUrlsFromFile(FILE_URLS);
        downloadFiles(urlsList);
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

    private static void downloadFiles(List<String> urlsList) {
        int totalChunks = 0;
        long totalSize = 0;
        for (String fileUrl : urlsList) {
            URL url;
            try {
                url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                totalSize += connection.getContentLength();
                totalChunks += Math.ceil((double) connection.getContentLength() / CHUNK_SIZE);
            } catch (SocketException e) {
                if (e.getMessage().equals("Network is unreachable (connect failed)")) {
                    System.err.println("[ERROR] Cannot connect to server: network is unreachable");
                    System.exit(1);
                } else {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int optimalThreadCount = Math.min(threadCount, (int) Math.ceil((double) totalChunks / 2));
        // System.out.println("Optimal thread count: " + optimalThreadCount);

        Queue<String> filesToDownload = new ArrayDeque<>(urlsList);
        int fileNumber = 1;
        while (!filesToDownload.isEmpty()) {
            String fileUrl = filesToDownload.remove();
            String fileName = getFileNameFromUrl(fileUrl);
            downloadFile(fileUrl, fileName, optimalThreadCount, fileNumber);

            fileNumber++;
        }
    }

    private static String getFileNameFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    private static void downloadFile(String fileUrl, String fileName, int optimalThreadCount, int fileNumber) {
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
                    break;
                } else {
                    System.out.println("Connection failed (" + responseCode + "): " + fileUrl);
                    System.out.println("Retrying...");
                    Thread.sleep(100);
                }
                if (System.currentTimeMillis() - startTime > timeout) {
                    System.out.println("Timeout reached: " + fileUrl);
                    System.exit(1);
                }
            } while (true);

            long fileSize = connection.getContentLength();

            int chunkCount = (int) Math.ceil((double) fileSize / CHUNK_SIZE);
            int threadCount = Math.min(optimalThreadCount, chunkCount);

            if (fileSize > 11 * 1024 * 1024 && threadCount + 2 <= optimalThreadCount) {
                threadCount += 2;
            }

            int chunksPerThread = (int) Math.ceil((double) chunkCount / threadCount);

            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            int i;
            for (i = 0; i < threadCount; i++) {
                int startChunk = i * chunksPerThread;
                int endChunk = Math.min((i + 1) * chunksPerThread - 1, chunkCount - 1);
                int startByte = startChunk * CHUNK_SIZE;
                int endByte = Math.min((endChunk + 1) * CHUNK_SIZE - 1, (int) fileSize - 1);

                if (endByte < startByte) {
                    continue;
                }
                executor.execute(new DownloadThread(fileUrl, startByte, endByte, DOWNLOAD_DIR, fileName));
                System.out.println("Thread-" + (i + 1) + " start download file number " + fileNumber);
            }
            // System.out.println("i = " + i);
            executor.shutdown();

            while (!executor.isTerminated()) {
                Thread.sleep(1000);
            }

            System.out.println("Thread-1 finish download file number " + fileNumber);

            for (int j = 2; j <= i; j++) {
                System.out.println("Thread-" + j + " finish download file number " + fileNumber);
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

                // range header
                String range = "bytes=" + startPos + "-" + endPos;
                connection.setRequestProperty("Range", range);

                try (InputStream inputStream = connection.getInputStream();
                        RandomAccessFile outputFile = new RandomAccessFile(downloadDir + "/" + fileName, "rw")) {

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
package ex00;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {

    private static Map<String, String> readSignatures(String inputFileName) {
        try (FileInputStream fileInputStream = new FileInputStream(new File(inputFileName));
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            Map<String, String> signatures = new HashMap<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String key = line.split(",")[0];
                String value = line.split(",")[1];
                signatures.put(key, value.trim());
            }
            return signatures;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }

    public static String getHexDump(String filename, int length) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] bytes = fis.readAllBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            byte[] decoded = Base64.getDecoder().decode(base64);
            String hex = byteToHex(decoded);
            return hex.substring(0, length + 7); // 7 spaces between bytes "25 50 44 46 2D 31 2E 35"
        }
    }

    public static void main(String[] args) {
        String inputFileName = "./signatures.txt";
        FileOutputStream outputStream = null;
        Map<String, String> signatures = readSignatures(inputFileName);
        File inputFile = new File(inputFileName);
        File outputFile = new File(inputFile.getParentFile(), "result.txt");

        try {
            if (outputFile.exists()) 
                Files.delete(outputFile.toPath());
            
            Files.createFile(outputFile.toPath());
            outputStream = new FileOutputStream(outputFile, true);
            if (outputFile.length() > 0)
                outputStream.write(System.lineSeparator().getBytes());
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // for (String key : signatures.keySet()) {
        // System.out.println(key + " : " + signatures.get(key));
        // }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String path = scanner.nextLine();
            if (path.equals("42")) {
                break;
            }
            path = path.trim();
            File file = new File(path);

            if (!file.exists()) {
                System.out.println("UNDEFINED");
                continue;
            } else if (file.isDirectory()) {
                System.out.println("UNDEFINED");
                continue;
            }

            String extension = path.substring(path.lastIndexOf(".") + 1).toUpperCase();

            try {
                String hexDump = getHexDump(path, 16);
                // System.out.println("hex = " + hexDump);
                String getExtensionSig = signatures.get(extension);
                // System.out.println("signature = " + getExtensionSig);
                if (getExtensionSig != null && hexDump.startsWith(getExtensionSig)) {
                    // System.out.println(extension + " --> " + signatures.get(extension));
                    System.out.println("PROCESSED");
                    extension = extension + "\n";
                    byte[] outputBytes = extension.getBytes();
                    outputStream.write(outputBytes);
                    continue;
                }
                System.out.println("UNDEFINED");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

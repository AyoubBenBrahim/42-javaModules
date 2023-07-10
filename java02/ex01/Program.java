package ex01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Program {

    public static String readFile(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append(" ");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return sb.toString();
    }

    private static int numerator(Vector<Integer> vector1, Vector<Integer> vector2) {
        int numerator = 0;
        for (int i = 0; i < vector1.size(); i++) {
            numerator += vector1.get(i) * vector2.get(i);
        }
        return numerator;
    }
    
    private static double denominator(Vector<Integer> vector1, Vector<Integer> vector2) {
        double magnitude1 = 0;
        double magnitude2 = 0;
        for (int i = 0; i < vector1.size(); i++) {
            magnitude1 += vector1.get(i) * vector1.get(i);
            magnitude2 += vector2.get(i) * vector2.get(i);
        }
        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);
        double denominator = magnitude1 * magnitude2;
        return denominator;
    }
        


    public static double cosineSimilarity(Vector<Integer> vector1, Vector<Integer> vector2) {
        int numerator = numerator(vector1, vector2);
        double denominator = denominator(vector1, vector2);

        

        if (denominator == 0) {
            return 0;
        } else {
            double similarity = numerator / denominator;

            return similarity;
            // return Double.parseDouble(String.format("%.2f", similarity));

        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ex01.Program file1 file2");
            return;
        }
        String fileA = args[0];
        String fileB = args[1];

        String inputA = readFile(fileA);
        System.out.println(inputA);

        System.out.println("----");
        String inputB = readFile(fileB);
        System.out.println(inputB);

        Map<String, Integer> dictionary = new TreeMap<String, Integer>();
        for (String word : (inputA + inputB).split(" ")) {
            if (!dictionary.containsKey(word)) {
                dictionary.put(word, dictionary.size());
            }
        }

        List<String> sortedWords = new ArrayList<>(dictionary.keySet());
        Collections.sort(sortedWords);

        try (PrintWriter writer = new PrintWriter("dictionary.txt")) {
            writer.println(String.join(" ", sortedWords));
        } catch (FileNotFoundException e) {
            System.out.println("Error writing dictionary file: " + e.getMessage());
        }

        int n = dictionary.size();
        Vector<Integer> vector1 = new Vector<>(Collections.nCopies(n, 0));
        Vector<Integer> vector2 = new Vector<>(Collections.nCopies(n, 0));

        // Set the frequency of each word in A
        for (String word : inputA.split(" ")) {
            int i = Collections.binarySearch(sortedWords, word);
            if (i >= 0) {
                vector1.set(i, vector1.get(i) + 1);
            }
        }

        // Set the frequency of each word in B
        for (String word : inputB.split(" ")) {
            int i = Collections.binarySearch(sortedWords, word);
            if (i >= 0) {
                vector2.set(i, vector2.get(i) + 1);
            }
        }

        System.out.println(sortedWords);
        System.out.println("A = " + vector1);
        System.out.println("B = " + vector2);

        // double similarity = cosineSimilarity(vector1, vector2);
        // DecimalFormat df = new DecimalFormat("#.##");
        // System.out.println("Cosine similarity between A and B: " +
        // df.format(similarity));

        double similarity = cosineSimilarity(vector1, vector2);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        String formattedSimilarity = df.format(similarity);
        System.out.println("Similarity = " + formattedSimilarity);

    }
}

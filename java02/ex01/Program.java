package ex01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Program {

    // public static Vector<String> readFile(String filename) {
    // Vector<String> lines = new Vector<String>();
    // try {
    // FileReader fileReader = new FileReader(filename);
    // BufferedReader bufferedReader = new BufferedReader(fileReader);
    // String line = null;
    // while ((line = bufferedReader.readLine()) != null) {
    // String[] words = line.split(" ");
    // for (String word : words) {
    // lines.add(word);
    // }
    // }
    // bufferedReader.close();
    // } catch (Exception e) {
    // System.out.println("Error reading file: " + e.getMessage());
    // }
    // return lines;
    // }

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

        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        for (String word : (inputA + inputB).split(" ")) {
            if (!dictionary.containsKey(word)) {
                dictionary.put(word, dictionary.size());
            }
        }

        Vector<Integer> vector1 = new Vector<Integer>(dictionary.size());
        Vector<Integer> vector2 = new Vector<Integer>(dictionary.size());

        for (int i = 0; i < dictionary.size(); i++) {
            vector1.add(0);
            vector2.add(0);
        }

        // Count the frequency of each word in A
        for (String word : inputA.split(" ")) {
            int index = dictionary.get(word);
            vector1.set(index, vector1.get(index) + 1);
        }

        // Count the frequency of each word in B
        for (String word : inputB.split(" ")) {
            int index = dictionary.get(word);
            vector2.set(index, vector2.get(index) + 1);
        }

        System.out.println(dictionary.keySet());
        System.out.println("A = " + vector1);
        System.out.println("B = " + vector2);
    }
}

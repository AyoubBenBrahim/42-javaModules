package ex02;

import java.util.Random;

public class Program {

    public static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1000) + 1;
        }
        return arr;
    }

    private static void printUsage() {
        System.err.println("Usage: java Program --arraySize=2000000 --threadsCount=4");
        System.exit(1);
    }

    private static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1)
                System.out.printf("%d]\n", arr[i]);
            else
                System.out.printf("%d, ", arr[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {

        int size = 0;
        int threadCount = 0;

        System.out.println("args.length: " + args.length);
        System.out.println("args[0]: " + args[0]);
        System.out.println("args[1]: " + args[1]);

        if (args.length != 2 || !args[0].contains("--arraySize=") || !args[1].contains("--threadsCount="))
            printUsage();

        try {
            size = Integer.parseInt(args[0].split("=")[1]);
            threadCount = Integer.parseInt(args[1].split("=")[1]);
        } catch (Exception e) {
            printUsage();
        }

        int[] arr = generateRandomArray(size);
        printArray(arr);

        NormalSum normalSum = new NormalSum(arr, size);
        System.out.println("Sum: " + normalSum.sum());

        // ParallelSum parallelSum = new ParallelSum(arr, count);
        // System.out.println(parallelSum.sum());

        int sectionSize = (int) Math.ceil((double) size / threadCount);

        SumThread[] threads = new SumThread[threadCount];
        Object lock = new Object();

        for (int i = 0; i < threadCount; i++) {
            int startIndex = i * sectionSize;
            int endIndex = Math.min((i + 1) * sectionSize, size);
            threads[i] = new SumThread(arr, startIndex, endIndex, i, lock);
            threads[i].start();
        }

        int sum = 0;
        for (SumThread thread : threads) {
            try {
                thread.join();
                synchronized (lock) {
                    sum += thread.getSum();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Sum by threads: " + sum);
    }
}

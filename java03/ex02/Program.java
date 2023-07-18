package ex02;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Program {

    public static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt() % 1000;
        }
        return arr;
    }

    private static void printUsage() {
        System.err.println("Usage: ./compile.sh --arraySize=2000000 --threadsCount=4");
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

        if (args.length != 2 || !args[0].contains("--arraySize=") || !args[1].contains("--threadsCount="))
            printUsage();

        try {
            size = Integer.parseInt(args[0].split("=")[1]);
            threadCount = Integer.parseInt(args[1].split("=")[1]);
            if (size < 1 || size > 2000000 || threadCount < 1 || threadCount > size)
                printUsage();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            printUsage();
        }

        int[] arr = generateRandomArray(size);
        // printArray(arr);

        NormalSum normalSum = new NormalSum(arr, size);
        System.out.println("Sum: " + normalSum.sum());

        int sectionSize = (int) Math.ceil((double) size / threadCount);

        // Object lock = new Object();
        // BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

        Semaphore[] semaphores = new Semaphore[threadCount];
        for (int i = 0; i < threadCount; i++) {
            semaphores[i] = new Semaphore(0);
        }

        SumThread[] threads = new SumThread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            int startIndex = i * sectionSize;
            int endIndex = Math.min((i + 1) * sectionSize, size);
            Semaphore semaphore = i == 0 ? new Semaphore(1) : semaphores[i - 1];
            Semaphore nextSemaphore = semaphores[i];
            threads[i] = new SumThread(arr, startIndex, endIndex, i, semaphore, nextSemaphore);
            threads[i].start();
        }

        int sum = 0;
        for (SumThread thread : threads) {
            try {
                thread.join();
                sum += thread.getSum();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Sum by threads: " + sum);
    }
}

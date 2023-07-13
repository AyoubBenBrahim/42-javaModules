package ex02;

import java.util.concurrent.BlockingQueue;

public class SumThread extends Thread {
    private final int[] arr;
    private final int startIndex;
    private final int endIndex;
    private final int threadId;
    private int sum;

    private final BlockingQueue<String> blockingQueue;

    public SumThread(int[] arr, int startIndex, int endIndex, int threadId, BlockingQueue<String> blockingQueue) {
        this.arr = arr;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.threadId = threadId;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            sum += arr[i];
        }
        String result = String.format("Thread %d: from %d to %d sum is %d", threadId+1, startIndex, endIndex - 1, sum);
        try {
            blockingQueue.put(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getSum() {
        System.out.println("Thread " + threadId + " sum: " + sum);
        return sum;

    }
}
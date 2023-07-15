package ex02;

import java.util.concurrent.Semaphore;

public class SumThread extends Thread {
    private final int[] arr;
    private final int startIndex;
    private final int endIndex;
    private final int threadId;
    private int sum;
    private final Semaphore semaphore;
    private final Semaphore nextSemaphore;

    public SumThread(int[] arr, int startIndex, int endIndex, int threadId, Semaphore semaphore, Semaphore nextSemaphore) {
        this.arr = arr;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.threadId = threadId;
        this.semaphore = semaphore;
        this.nextSemaphore = nextSemaphore;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            sum += arr[i];
        }
        try {
            semaphore.acquire();
            System.out.printf("Thread %d: from %d to %d sum is %d\n", threadId+1, startIndex, endIndex - 1, sum);
            nextSemaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }
}
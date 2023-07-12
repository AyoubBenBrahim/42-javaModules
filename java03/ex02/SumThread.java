package ex02;

public class SumThread extends Thread {
    private final int[] arr;
    private final int startIndex;
    private final int endIndex;
    private final int threadId;
    private int sum;
    private final Object lock;

    public SumThread(int[] arr, int startIndex, int endIndex, int threadId, Object lock) {
        this.arr = arr;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.threadId = threadId;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            sum += arr[i];
        }
        synchronized (lock) {
            System.out.printf("Thread %d: from %d to %d sum is %d\n", threadId, startIndex, endIndex - 1, sum);
        }
    }

    public int getSum() {
        return sum;
    }
}
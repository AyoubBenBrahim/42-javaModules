package ex00;

public class eggImplementingRunnable implements Runnable{
    private int count;

    public eggImplementingRunnable(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Egg");
        }
    }
}

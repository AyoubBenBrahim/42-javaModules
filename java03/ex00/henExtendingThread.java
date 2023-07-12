package ex00;

public class henExtendingThread extends Thread {
    private int count;

    public henExtendingThread(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Hen");
            // try {
            //     Thread.sleep(10);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        }
    }

}

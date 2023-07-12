package ex01;

public class henExtendingThread extends Thread {
    private int count;

    public henExtendingThread(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++)
            Program.printMessage("Hen");
    }

}

package ex01;

public class Program {
    private static final Object lock = new Object();
    private static int count;
    private static int henCount = 0;
    private static int eggCount = 0;

    public static void printMessage(String message) {
        synchronized (lock) {
            while ((message.equals("Hen") && eggCount == henCount) || (message.equals("Egg") && eggCount > henCount)) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(message);
            if ("Hen".equals(message))
                henCount++;
            else
                eggCount++;

            lock.notifyAll();
        }
    }

    public static void main(String[] args) {

        count = getCount(args);

        henExtendingThread hen = new henExtendingThread(count);
        Thread egg = new Thread(new eggImplementingRunnable(count));

        hen.start();
        egg.start();

        try {
            hen.join();
            egg.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int getCount(String[] args) {
        if (args.length != 1 || !args[0].contains("--count=")) {
            System.err.println("Usage: java Program --count=5");
            System.exit(1);
        }

        try {
            count = Integer.parseInt(args[0].split("=")[1]);
        } catch (NumberFormatException e) {
            System.err.println("Enter a Valid number");
            System.err.println("Usage: java Program --count=5");
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Usage: java Program --count=5");
            System.exit(1);
        }
        return count;
    }

}

// ./compile.sh ex01 --count=50 | grep -o "Hen\|Egg" | sort | uniq -c
// 50 Egg
// 50 Hen
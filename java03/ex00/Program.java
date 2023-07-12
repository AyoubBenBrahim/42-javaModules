package ex00;

public class Program {
    private static int count;

    public static void main(String[] args) {

        if (args.length != 1 || !args[0].contains("--count=")) {
            System.err.println("Usage: java Program --count=5");
            System.exit(1);
        }

        count = Integer.parseInt(args[0].split("=")[1]);

        henExtendingThread hen = new henExtendingThread(count);
        Thread egg = new Thread(new eggImplementingRunnable(count));
        Human human = new Human(count);

        hen.start();
        egg.start();

        try {
            hen.join();
            egg.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        human.start();
    }
}
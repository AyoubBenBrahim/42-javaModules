package ex00;

public class Human {
    private int count;

    public Human(int count) {
        this.count = count;
    }

    public void start() {
        for (int i = 0; i < count; i++) {
            System.out.println("Human");
        }
    }
}
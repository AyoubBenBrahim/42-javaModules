package java01.ex00;

public class User {
    private static int nextID = 1;

    private final int identifier;
    private String name;
    private int balance;

    public User(String name, int balance) {
        this.identifier = nextID;
        nextID++;

        this.name = name;
        this.balance = balance;

        if (balance < 0)
            balance = 0;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        if (balance < 0)
            this.balance = 0;
    }

    public String toString() {
        return "    {" +
                "ID=" + identifier +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

}
package java01.ex01;

public class User {
    private final Integer identifier;
    private String name;
    private Integer balance;

    public User(String name, Integer balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = balance;

        if (balance < 0)
            this.balance = 0;
    }

    public Integer getIdentifier() {return identifier;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Integer getBalance() {return balance;}

    public void setBalance(Integer balance) {this.balance = balance;}

    public String toString() {
        return "    {" +
                "ID=" + identifier +
                " , name='" + name + '\'' +
                " , balance=" + balance +
                '}';
    }
}
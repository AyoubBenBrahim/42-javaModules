package java01.ex04;

public class User {
    private final Integer identifier;
    private String name;
    private Integer balance;
    private TransactionsList transactionsList;

    
    
    public User(String name, Integer balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = balance;
        this.transactionsList = new TransactionsLinkedList();
        
        if (balance < 0)
        this.balance = 0;
    }
    
    public TransactionsList getTransactionsList() {
         return transactionsList;
    }

    public void setTransactionsList(TransactionsList transactionsList) {
        this.transactionsList = transactionsList;
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
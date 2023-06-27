package java01.ex03;

import java.util.UUID;

public interface TransactionsList {

    public void addTransaction(Transaction transaction);

    // public Transaction getTransactionById(Integer id);

    // public Transaction getTransactionByIndex(Integer index);

    // public Integer getTransactionsCount();

    public void removeTransactionById(UUID uuid);

    // public void removeTransactionByIndex(Integer index);

    // public void removeTransaction(Transaction transaction);

    // public void clearTransactions();

    public Transaction[] toArray();

    public void printTransactionsArray();
    
}
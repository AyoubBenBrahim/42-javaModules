package java01.ex05;

import java.util.UUID;

public interface TransactionsList {

    public void addTransaction(Transaction transaction);

    public void removeTransactionById(UUID uuid);

    public Transaction[] toArray();

    public void printTransactionsArray();
    
}
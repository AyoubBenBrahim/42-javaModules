package java01.ex03;

import java.util.UUID;

public class Program {

    public static void main(String[] args) {

        UUID randomUUID = UUID.randomUUID();

        User user1 = new User("user1", 1000);
        User user2 = new User("user2", 2000);
        User user3 = new User("user3", 3000);
        User user4 = new User("user4", 4000);

        TransactionsLinkedList transactionsList = new TransactionsLinkedList();
        UsersArrayList usersList = new UsersArrayList();

        usersList.addUser(user1);
        usersList.addUser(user2);
        usersList.addUser(user3);
        usersList.addUser(user4);

        System.out.println("Users list:");
        for (int i = 0; i < usersList.getUsersCount(); i++) {
            System.out.println(usersList.getUserByIndex(i));
        }

        Transaction transaction1 = new Transaction(usersList.getUserByIndex(0), usersList.getUserByIndex(1), 1000,
                Transaction.TransferCategory.DEBIT);
        Transaction transaction2 = new Transaction(usersList.getUserById(2), user3, 2000,
                Transaction.TransferCategory.CREDIT);
        Transaction transaction3 = new Transaction(user3, user4, 3000, Transaction.TransferCategory.DEBIT);

        for (int i = 0; i < usersList.getUsersCount(); i++) {
            usersList.getUserByIndex(i).getTransactionsList().addTransaction(transaction1);
            usersList.getUserByIndex(i).getTransactionsList().addTransaction(transaction2);
            usersList.getUserByIndex(i).getTransactionsList().addTransaction(transaction3);
        }

        System.out.println();
        System.out.println("Transactions list:");
        usersList.getUserByIndex(0).getTransactionsList().printTransactionsArray();

        // transactionsList.addTransaction(transaction1);
        // transactionsList.printTransactionsArray();

        try {
            // transactionsList.removeTransactionById(transaction2.getUuid());
            // transactionsList.printTransactionsArray();

            usersList.getUserById(1).getTransactionsList().removeTransactionById(transaction2.getUuid());
            usersList.getUserByIndex(0).getTransactionsList().printTransactionsArray();

            usersList.getUserById(1).getTransactionsList().removeTransactionById(randomUUID);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
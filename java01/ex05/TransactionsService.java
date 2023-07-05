package java01.ex05;

import java.util.UUID;

public class TransactionsService {

    private UsersArrayList userList;

    public TransactionsService() {
        this.userList = new UsersArrayList();
    }

    public void addUser(User user) {
        userList.addUser(user);
    }

    public Integer getUserBalance(User user) {
        if (user == null)
            throw new MyExceptions.UserNotFoundException("User not found");

        return user.getBalance();
    }

    public Integer getUserBalanceById(Integer userID) {
        // return userList.getUserById(userID).getBalance();
        User user = userList.getUserById(userID);
        if (user == null) {
            throw new MyExceptions.UserNotFoundException("User not found");
        }
        return user.getBalance();
    }

    public String getUserNameById(Integer userID) {
        // return userList.getUserById(userID).getName();
        User user = userList.getUserById(userID);
        if (user == null) {
            throw new MyExceptions.UserNotFoundException("User not found");
        }
        for (User user1 : userList.getUsersList()) {
            if (user1.getIdentifier() == userID)
                return user1.getName();
        }
        throw new MyExceptions.UserNotFoundException("User Not Found");
    }

    public User getUserById(Integer userID) {
        // return userList.getUserById(userID).getName();
        User user = userList.getUserById(userID);
        if (user == null) {
            throw new MyExceptions.UserNotFoundException("User not found");
        }
        for (User user1 : userList.getUsersList()) {
            if (user1.getIdentifier() == userID) 
                return user1;
        }
        throw new MyExceptions.UserNotFoundException("User Not Found");
    }

    public Transaction[] getTransactionsByUser(User user) {
        Transaction[] transactionList = user.getTransactionsList().toArray();
        Transaction[] transactionListByUser = new Transaction[transactionList.length];
        int size = 0;
        for (Transaction transaction : transactionList) {
            if (transaction.getSender() == user || transaction.getRecipient() == user) {
                transactionListByUser[size] = transaction;
                size++;
            }
        }
        Transaction[] transactionListByUserFinal = new Transaction[size];
        for (int i = 0; i < size; i++) {
            transactionListByUserFinal[i] = transactionListByUser[i];
        }
        return transactionListByUserFinal;
    }

    public void removeTransactionById(UUID transUuid, Integer userID) {
        User user = userList.getUserById(userID);
        user.getTransactionsList().removeTransactionById(transUuid);
    }

    public Transaction[] getUnpairedTransactions() {
        TransactionsLinkedList unpairTransactions = new TransactionsLinkedList();

        for (int i = 0; i < userList.getUsersCount(); i++) {
            Transaction[] transactionsArray = userList.getUserByIndex(i).getTransactionsList().toArray();
            for (Transaction transaction : transactionsArray) {
                if (transaction.getTransferCategory() == Transaction.TransferCategory.INVALID) {
                    unpairTransactions.addTransaction(transaction);
                }
            }

        }
        return unpairTransactions.toArray();
    }

    public void performTransfer(Integer senderID, Integer recipientID, Integer transferAmount) {

        if (senderID == 0 || recipientID == 0 || transferAmount < 0 || senderID == recipientID)
            throw new MyExceptions.IllegalTransactionException("IDs Invalid");

        if (userList.getUserById(senderID).getBalance() < transferAmount)
            throw new MyExceptions.IllegalTransactionException("Insufficient funds");

        User sender = userList.getUserById(senderID);
        User recipient = userList.getUserById(recipientID);

        UUID uuid = UUID.randomUUID();
        Transaction debitTransaction = new Transaction(uuid, sender, recipient, transferAmount,
                Transaction.TransferCategory.DEBIT);
        Transaction creditTransaction = new Transaction(uuid, sender, recipient, transferAmount,
                Transaction.TransferCategory.CREDIT);

        // declared uuid in Transaction class as final, so stter can't be used
        // debitTransaction.setUuid(uuid);
        // creditTransaction.setUuid(uuid);

        sender.getTransactionsList().addTransaction(debitTransaction);
        recipient.getTransactionsList().addTransaction(creditTransaction);

        debitTransaction.performTransfer();
    }
}

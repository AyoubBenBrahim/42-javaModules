package java01.ex03;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private TransactionNode head = null;
    private TransactionNode tail = null;
    private Integer size = 0;

    @Override
    public void addTransaction(Transaction transaction) {
        if (transaction == null)
            return;

        TransactionNode newNode = new TransactionNode(transaction);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void removeTransactionById(UUID uuid) {

        TransactionNode current = head;
        TransactionNode previous = null;

        if (uuid == null) {
            throw new TransactionNotFoundException("Transaction not found");
        }

        while (current != null) {
            if (current.getTransaction().getUuid().equals(uuid)) {
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                return;
            }
            previous = current;
            current = current.getNext();
        }
        throw new TransactionNotFoundException("TransactionNotFoundException");
    }

    @Override
    public Transaction[] toArray() {
        if (size == 0 || head == null)
            return null;

        Transaction[] transactionsArr = new Transaction[size];
        TransactionNode current = head;
        Integer index = 0;
        while (current != null) {
            transactionsArr[index] = current.getTransaction();
            current = current.getNext();
            index++;
        }

        return transactionsArr;
    }

    @Override
    public void printTransactionsArray() {
        Transaction[] transactionsArr = toArray();
        for (Transaction transaction : transactionsArr) {
            System.out.println(transaction.getUuid());
        }
        System.out.println("=============");
    }
}
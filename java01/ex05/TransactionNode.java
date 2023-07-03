package java01.ex05;

public class TransactionNode {

    private Transaction transaction;
    private TransactionNode next;

    public TransactionNode(Transaction transac) {
        this.transaction = transac;
        this.next = null;
    }

    public TransactionNode(Transaction transac, TransactionNode next) {
        this.transaction = transac;
        this.next = next;
    }

    // public Transaction getTransaction() {return transaction;}

    // public void setTransaction(Transaction transaction) {this.transaction = transaction;}

    public TransactionNode getNext() {return next;}

    public void setNext(TransactionNode next) {this.next = next;}

    // public Transaction findTransaction(Transaction transaction) {
    //     TransactionNode current = this;
    //     while (current != null) {
    //         if (current.getTransaction().getUuid().equals(transaction.getUuid())) {
    //             return current.getTransaction();
    //         }
    //         current = current.getNext();
    //     }
    //     return null;
    // }
        
    
    public Transaction getTransaction() {return transaction;}
    
}

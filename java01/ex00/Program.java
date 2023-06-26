
package java01.ex00;

public class Program {

    public static void main(String[] args) {
        User user1 = new User("L7sen", 1000);
        User user2 = new User("L3rbi", 5000);

        System.out.println("User 1 details: \n" + user1.toString());
        System.out.println("User 2 details: \n" + user2.toString());

        System.out.println();

        Transaction transaction1 = new Transaction(user1, user2, 1000, Transaction.TransferCategory.DEBIT);

        System.out.println("Transaction 1 Details:\n" + transaction1.toString());
        transaction1.performTransfer();
        System.out.println();
        System.out.println("Transaction 1 After DEBIT (-) Transaction:\n" + transaction1.toString());


        System.out.printf("--------\n\n");


        Transaction transaction2 = new Transaction(user1, user2, 1000, Transaction.TransferCategory.CREDIT);

        System.out.println("Transaction 2 Details:\n" + transaction2.toString());
        transaction2.performTransfer();
        System.out.println();
        System.out.println("Transaction 2 After CREDIT (+) Transaction:\n" + transaction2.toString());
    }
}
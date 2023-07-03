package java01.ex04;

public class Program {

    public static void main(String[] args) {

        TransactionsService transactionService = new TransactionsService();

        User user1 = new User("user1", 6000);
        User user2 = new User("user2", 3000);

        transactionService.addUser(user1);
        transactionService.addUser(user2);

        System.out.println("getUserBalance(user1) = " + transactionService.getUserBalance(user1));
        System.out.println("getUserBalance(user2) = " + transactionService.getUserBalance(user2));
        System.out.println("user1" + user1);
        System.out.println("user2" + user2);
        System.out.println("-----------------------");

        transactionService.performTransfer(user1.getIdentifier(), user2.getIdentifier(), 1000);

        System.out.println("-----------------------");

        System.out.println("getUserBalance(user1) = " + transactionService.getUserBalance(user1));
        System.out.println("getUserBalance(user2) = " + transactionService.getUserBalance(user2));
          System.out.println("user1" + user1);
        System.out.println("user2" + user2);
        System.out.println("-----------------------");

        try {
            for(Transaction transaction : transactionService.getTransactionsByUser(user1))
                System.out.println( "transaction Details --> " + transaction);
            
            System.out.println("TransactionsList :");
            user1.getTransactionsList().printTransactionsArray();

            System.out.println("removeTransactionById");
            transactionService.removeTransactionById(user1.getTransactionsList().toArray()[0].getUuid(), user1.getIdentifier());

            System.out.println("TransactionsList after remove :");
            user1.getTransactionsList().printTransactionsArray();

        } catch (MyExceptions.UserNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (MyExceptions.TransactionNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
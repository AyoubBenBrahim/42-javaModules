package java01.ex05;

public class Menu {

    TransactionsService Service;
    private boolean dev;

    public Menu(boolean dev) {
        this.dev = dev;
        Service = new TransactionsService();
    }

    public void printMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transaction for a specific user");
        if (dev) {
            System.out.println("5. DEV - remove a transfer by ID");
            System.out.println("6. DEV - check tarnsfer validity");
            System.out.println("7. Finish execution");
        } else {
            System.out.println("5. Finish execution");
        }
    }

    public void addUser() {
        System.out.println("Enter a user name and a balance:");
        String name = System.console().readLine();
        String balance = System.console().readLine();
        User user = new User(name, Integer.parseInt(balance));
        Service.addUser(user);
    }

    public void viewUserBalances() {
        System.out.println("Enter user name:");
        String name = System.console().readLine();
        User user = new User(name, 0);
        System.out.println("User balance: " + Service.getUserBalance(user));
    }

    public void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount:");

  

        try {
            Service.performTransfer(sourceName, destinationName, Integer.parseInt(amount));
        } catch (MyExceptions.UserNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (MyExceptions.TransactionNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (MyExceptions.NotEnoughMoneyException ex) {
            System.out.println(ex.getMessage());
        } catch (MyExceptions.TransferNotValidException ex) {
            System.out.println(ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void viewAllTransactionsByUser() {
        System.out.println("Enter a user ID:");
        Integer id = Integer.parseInt(System.console().readLine());
        User user = ;
        try {
            for (Transaction transaction : Service.getTransactionsByUser(user)) {
                System.out.println("transaction Details --> " + transaction);
            }
        } catch (MyExceptions.UserNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (MyExceptions.TransactionNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeTransactionById() {
        System.out.println("Enter a user ID and a transfer UUID:");
        Integer id = Integer.parseInt(System.console().readLine());
        try {
            Service.removeTransactionById(id);
        } catch (MyExceptions.TransactionNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void checkTransferValidity() {
        // System.out.println("Enter a sender ID, a recipient ID, and a transfer amount:");
        String sourceName = System.console().readLine();
        String destinationName = System.console().readLine();
        String amount = System.console().readLine();
        try {
            Service.checkTransferValidity(sourceName, destinationName, Integer.parseInt(amount));
        } catch (MyExceptions.UserNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (MyExceptions.TransactionNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (MyExceptions.NotEnoughMoneyException ex) {
            System.out.println(ex.getMessage());
        } catch (MyExceptions.TransferNotValidException ex) {
            System.out.println(ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

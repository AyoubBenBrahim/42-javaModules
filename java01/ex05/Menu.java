package java01.ex05;

import java.util.InputMismatchException;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a user name and a balance");

        while (true) {
            String[] args = scanner.nextLine().split(" ");

            if (args.length != 2) {
                System.err.println("Invalid Input, Try Again");
                continue;
            }
            try {
                Integer balance = Integer.parseInt(args[1]);
                User user = new User(args[0], balance);
                Service.addUser(user);
                System.out.printf("User with id = %d is added\n", user.getIdentifier());
                return;
            } catch (NumberFormatException ex) {
                System.err.println("Invalid Input, Try Again");
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void viewUserBalances() {
        System.out.println("Enter a user ID:");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Integer userID = scanner.nextInt();
                System.out.println(Service.getUserNameById(userID) + " - " + Service.getUserBalanceById(userID));
                return;
            } catch (MyExceptions.UserNotFoundException e) {
                System.err.println("UserNotFound Try Again");
            } catch (InputMismatchException e) {
                System.err.println("Enter a valid ID");
                scanner.nextLine();
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    public void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount:");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] args = scanner.nextLine().split(" ");

            if (args.length != 3) {
                System.err.println("Invalid Input, Try Again");
                continue;
            }

            try {
                Integer senderID = Integer.parseInt(args[0]);
                Integer recipientID = Integer.parseInt(args[1]);
                Integer transferAmount = Integer.parseInt(args[2]);
                Service.performTransfer(senderID, recipientID, transferAmount);
                return;
            } catch (MyExceptions.UserNotFoundException ex) {
                System.err.println("UserNotFound Try Again");
            } catch (MyExceptions.TransactionNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (InputMismatchException e) {
                System.err.println("Enter a valid Integer");
                scanner.nextLine();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void viewAllTransactionsPerUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a user ID:");

        while (true) {
              String[] args = scanner.nextLine().split(" ");

            if (args.length != 1) {
                System.err.println("Invalid Args, Try Again");
                continue;
            }
            try {
                Integer userID = Integer.parseInt(args[0]);
                String userName = Service.getUserNameById(userID);
                for (Transaction transaction : Service.getTransactionsByUser(userName)) {
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
    }

    public void removeTransactionById() {
        System.out.println("Enter a user ID and a transfer UUID:");
        Integer id = Integer.parseInt(System.console().readLine());
        try {
            // Service.removeTransactionById(id);
        } catch (MyExceptions.TransactionNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void checkTransferValidity() {
        // System.out.println("Enter a sender ID, a recipient ID, and a transfer
        // amount:");
        String sourceName = System.console().readLine();
        String destinationName = System.console().readLine();
        String amount = System.console().readLine();
        try {
            // Service.checkTransferValidity(sourceName, destinationName,
            // Integer.parseInt(amount));
        } catch (MyExceptions.UserNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (MyExceptions.TransactionNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void dispatcher(int choice) {
        switch (choice) {
            case 1:
                addUser();
                break;
            case 2:
                viewUserBalances();
                break;
            case 3:
                performTransfer();
                System.out.println("The transfer is completed");
                break;
            case 4:
                viewAllTransactionsPerUser();
                break;
            case 5:
                if (dev) {
                    removeTransactionById();
                } else {
                    System.exit(0);
                }
                break;
            case 6:
                if (dev) {
                    checkTransferValidity();
                } else {
                    System.exit(0);
                }
                break;
            case 7:
                System.exit(0);
                break;
            default:
                System.err.println("illegal Operation");
                System.exit(-1);
        }
    }
}

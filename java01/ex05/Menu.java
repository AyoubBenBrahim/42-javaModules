package java01.ex05;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

import java01.ex05.Transaction.TransferCategory;

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
            System.out.println("6. DEV - check transfer validity");
        }
        System.out.println("7. Finish execution");
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

    private void formatOutput(String userName, Integer userID, Transaction transaction) {
        String category = transaction.getTransferCategory().toString();
        Integer transferAmount = transaction.getTransferAmount();
        String transactionID = transaction.getUuid().toString();

        String action = category.equals("CREDIT") ? "From " : "To ";
        String sign = category.equals("CREDIT") ? ") +" : ") -";

        System.out.println(action + userName + "(id = " + userID + sign + transferAmount + " with id = " + transactionID);
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
                for (Transaction transaction : Service.getTransactionsByUser(Service.getUserById(userID))) {
                    if (transaction.getTransferCategory() == TransferCategory.DEBIT) {
                        Integer recipientID = transaction.getRecipient().getIdentifier();
                        String recipientName = Service.getUserNameById(transaction.getRecipient().getIdentifier());

                        formatOutput(recipientName, recipientID, transaction);

                    } else if (transaction.getTransferCategory() == TransferCategory.CREDIT) {
                        Integer senderID = transaction.getSender().getIdentifier();
                        String senderName = Service.getUserNameById(transaction.getSender().getIdentifier());

                        formatOutput(senderName, senderID, transaction);
                    }
                }
                return;
            } catch (MyExceptions.UserNotFoundException ex) {
                System.out.println(ex.getMessage());
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

    public void removeTransactionById() {
        System.out.println("Enter a user ID and a transfer UUID:");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String[] args = scanner.nextLine().split(" ");

            if (args.length != 2) {
                System.err.println("Invalid Args, Try Again");
                continue;
            }

            try {
                Integer senderID = Integer.parseInt(args[0]);
                UUID transactionID = UUID.fromString(args[1]);
                Integer arr[] = Service.getTransactionRecipientByUUID(Service.getUserById(senderID), transactionID);
                Integer recipientID = arr[0];
                Integer transferAmount = arr[1];
                String action = (arr[2] == 0) ? "To " : "From ";

                Service.removeTransactionByUUID(transactionID, senderID);
                // Transfer To Mike(id = 2) 150 removed
                System.out.println("Transfer " + action + Service.getUserNameById(recipientID) + "(id = " + recipientID + ") " + transferAmount + " removed");
                return;
            } catch (MyExceptions.UserNotFoundException ex) {
                System.out.println(ex.getMessage());
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

    public void checkTransferValidity() {
        try {
            Transaction[] tranasctions = Service.getUnpairedTransactions();
            if (tranasctions == null || tranasctions.length == 0) {
                System.out.println("No Unacknowledged Transactions");
            } else {
                System.out.println("Check Results");
                for (Transaction tranasction : tranasctions) {
                    System.out.println(tranasction.getRecipient().getName()
                            + "(id = " + tranasction.getRecipient().getIdentifier()
                            + ") has an unacknowledged id = " + tranasction.getUuid().toString()
                            + " from " + tranasction.getSender().getName() + "(id = "
                            + tranasction.getSender().getIdentifier() + ") for " + tranasction.getTransferAmount());
                }
            }
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
                if (dev)
                    removeTransactionById();
                break;
            case 6:
                if (dev)
                    checkTransferValidity();
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

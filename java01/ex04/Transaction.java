package java01.ex04;

import java.util.UUID;

public class Transaction {

    public enum TransferCategory {
        DEBIT, CREDIT, INVALID
    }

    private final UUID identifier;
    private User recipient;
    private User sender;
    private Integer transferAmount;
    private TransferCategory transferCategory;

    public Transaction(User recipiente, User sender, Integer transferAmount, TransferCategory transferCategory) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipiente;
        this.sender = sender;
        this.transferAmount = transferAmount;
        this.transferCategory = transferCategory;

        if (transferAmount < 0)
            this.transferAmount = 0;

        if (transferCategory == TransferCategory.DEBIT || transferCategory == TransferCategory.CREDIT)
            this.transferCategory = transferCategory;
        else
            this.transferCategory = TransferCategory.INVALID;

    }

    public UUID getUuid() {return identifier;}

    public User getRecipient() {return recipient;}

    public void setRecipient(User recipient) {this.recipient = recipient;}

    public User getSender() {return sender;}

    public void setSender(User sender) {this.sender = sender;}

    public Integer getTransferAmount() {return transferAmount;}

    public void setTransferAmount(Integer transferAmount) {
        if (transferCategory != TransferCategory.INVALID) {
            this.transferAmount = transferAmount;
        } else {
            System.out.println("Invalid transfer category");
            System.exit(0);
        }
        if (transferAmount < 0) {
            this.transferAmount = 0;
        }
    }

    public String getTransferCategory() {return transferCategory.toString();}

    public void setTransferCategory(TransferCategory transferCategory) {
        if (transferCategory == TransferCategory.DEBIT || transferCategory == TransferCategory.CREDIT)
            this.transferCategory = transferCategory;
        else
            this.transferCategory = TransferCategory.INVALID;
    }

    public void performTransfer() {
        if (sender.getBalance() < transferAmount) {
            System.out.println("Insufficient funds");
            System.exit(0);
        }

        if (transferCategory == TransferCategory.DEBIT) {
            // Deduct transfer amount from sender's account
            sender.setBalance(sender.getBalance() - transferAmount);
            // Add transfer amount to recipient's account
            recipient.setBalance(recipient.getBalance() + transferAmount);

            String output = sender.getName() + " -> " + recipient.getName() + ", -" + transferAmount + ", OUTCOME, "
                    + identifier.toString();
            System.out.println(output);
            output = recipient.getName() + " -> " + sender.getName() + ", +" + transferAmount + ", INCOME, "
                    + identifier.toString();
            System.out.println(output);
        } else if (transferCategory == TransferCategory.CREDIT) {
            // Add transfer amount to sender's account
            sender.setBalance(sender.getBalance() + transferAmount);
            // Deduct transfer amount from recipient's account
            recipient.setBalance(recipient.getBalance() - transferAmount);

            // String output = recipient.getName() + " -> " + sender.getName() + ", +" +
            // transferAmount + ", INCOME, " + identifier.toString();
            // System.out.println(output);

            String output = recipient.getName() + " -> " + sender.getName() + ", -" + transferAmount + ", OUTCOME, "
                    + identifier.toString();
            System.out.println(output);
            output = sender.getName() + " -> " + recipient.getName() + ", +" + transferAmount + ", INCOME, "
                    + identifier.toString();
            System.out.println(output);
        } else {
            System.out.println("Invalid transfer category");
            System.exit(0);
        }

    }

    public String toString() {
        return "     UUID: " + identifier.toString() + "\n" + "     sender: " + sender.getName()
                + "\n"
                + "     recipient: " + recipient.getName() + "\n" + "     amount: " + transferAmount
                + "\n" + "     category: " + transferCategory + "\n" + "     sender balance: "
                + sender.getBalance() + "\n" + "     recipient balance: " + recipient.getBalance() + "\n"
                + "     sender name: " + sender.getName() + "\n" + "     recipient name: "
                + recipient.getName() + "\n" + "     sender id: " + sender.getIdentifier() + "\n"
                + "     recipient id: " + recipient.getIdentifier() + "\n";
    }
}
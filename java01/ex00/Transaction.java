package java01.ex00;

import java.util.UUID;

public class Transaction {

    public enum TransferCategory {
        DEBIT, CREDIT, INVALID
    }

    final UUID identifier;
    private User recipient;
    private User sender;
    private int transferAmount;
    TransferCategory transferCategory;

    public Transaction(User recipiente, User sender, int transferAmount, TransferCategory transferCategory) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipiente;
        this.sender = sender;
        this.transferAmount = transferAmount;
        this.transferCategory = transferCategory;

        if (transferAmount < 0)
            transferAmount = 0;

        if (transferCategory == TransferCategory.DEBIT || transferCategory == TransferCategory.CREDIT)
            this.transferCategory = transferCategory;
        else
            this.transferCategory = TransferCategory.INVALID;

    }

    public String getUuid() {
        return identifier.toString();
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(int transferAmount) {
        if (transferCategory != TransferCategory.INVALID) {
            this.transferAmount = transferAmount;
        } else {
            System.out.println("Invalid transfer category");
            return;
        }
        if (transferAmount < 0) {
            this.transferAmount = 0;
        }
    }

    public String getTransferCategory() {
        return transferCategory.toString();
    }

    public void setTransferCategory(TransferCategory transferCategory) {
        if (transferCategory == TransferCategory.DEBIT || transferCategory == TransferCategory.CREDIT)
            this.transferCategory = transferCategory;
        else
            this.transferCategory = TransferCategory.INVALID;

    }

    public void performTransfer() {
        if (sender.getBalance() < transferAmount) {
            System.out.println("Insufficient funds");
            return;
        }

        if (transferCategory == TransferCategory.DEBIT) {
            // Deduct transfer amount from sender's account
            sender.setBalance(sender.getBalance() - transferAmount);
            // Add transfer amount to recipient's account
            recipient.setBalance(recipient.getBalance() + transferAmount);
        } else if (transferCategory == TransferCategory.CREDIT) {
            // Add transfer amount to sender's account
            sender.setBalance(sender.getBalance() + transferAmount);
            // Deduct transfer amount from recipient's account
            recipient.setBalance(recipient.getBalance() - transferAmount);
        } else {
            System.out.println("Invalid transfer category");
            return;
        }

        System.out.println("Transfer successful");
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
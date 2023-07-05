package java01.ex05;

import java.util.UUID;

public class Transaction {

    public enum TransferCategory {
        DEBIT, CREDIT, INVALID
    }

    private final UUID identifier;
    private User sender;
    private User recipient;
    private Integer transferAmount;
    private TransferCategory transferCategory;

    public Transaction(UUID uuid, User sender, User recipiente, Integer transferAmount,
            TransferCategory transferCategory) {
        this.identifier = uuid;
        this.sender = sender;
        this.recipient = recipiente;
        this.transferAmount = transferAmount;
        this.transferCategory = transferCategory;

        if (transferAmount < 0)
            this.transferAmount = 0;

        if (transferCategory == TransferCategory.DEBIT || transferCategory == TransferCategory.CREDIT)
            this.transferCategory = transferCategory;
        else
            this.transferCategory = TransferCategory.INVALID;
    }

    public UUID getUuid() {
        return identifier;
    }

    public User getRecipient() {
        return this.recipient;
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

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        if (transferCategory != TransferCategory.INVALID)
            this.transferAmount = transferAmount;
        else {
            System.out.println("Invalid Transfer Category");
            System.exit(0);
        }
        if (transferAmount < 0) {
            this.transferAmount = 0;
        }
    }

    public TransferCategory getTransferCategory() {
        return transferCategory;
    }

    public void setTransferCategory(TransferCategory transferCategory) {
        if (transferCategory == TransferCategory.DEBIT || transferCategory == TransferCategory.CREDIT)
            this.transferCategory = transferCategory;
        else
            this.transferCategory = TransferCategory.INVALID;
    }

    public void performTransfer() {

        // if (transferCategory == TransferCategory.DEBIT) {
            sender.setBalance(sender.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);
        
        // } else if (transferCategory == TransferCategory.CREDIT) {
        //     sender.setBalance(sender.getBalance() + transferAmount);
        //     recipient.setBalance(recipient.getBalance() - transferAmount);
        // }
    }

    public String toString() {
        return "     UUID: " + identifier.toString() + "\n" + "     sender: " + sender.getName()
                + "\n"
                + "     recipient: " + recipient.getName() + "\n" + "     amount: " + transferAmount
                + "\n" + "     sender id: " + sender.getIdentifier() + "\n"
                + "     recipient id: " + recipient.getIdentifier() + "\n"
                + "     category: " + transferCategory + "\n" + "     sender balance: "
                + sender.getBalance() + "\n" + "     recipient balance: " + recipient.getBalance() + "\n";

    }
}
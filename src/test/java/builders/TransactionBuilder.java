package builders;

import jacekjagiello.inggreencoding.entities.transactions.Transaction;
import utils.RandomUtils;

public class TransactionBuilder extends RandomUtils {
    private String debitAccount;
    private String creditAccount;
    private Double amount;

    private final String[] possibleRandomDebitAccounts = {"012", "345", "678", "900", "101", "102", "103"};
    private final String[] possibleRandomCreditAccounts = {"104", "105", "106", "107", "108", "109", "110"};

    public TransactionBuilder withDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
        return this;
    }

    public TransactionBuilder withCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
        return this;
    }

    public TransactionBuilder forAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder withRandomDebitAccount() {
        this.debitAccount = this.randomStringElement(this.possibleRandomDebitAccounts);
        return this;
    }

    public TransactionBuilder withRandomCreditAccount() {
        this.creditAccount = this.randomStringElement(this.possibleRandomCreditAccounts);
        return this;
    }

    public TransactionBuilder forRandomAmount() {
        this.amount = this.randomDouble(1, 50);
        return this;
    }

    public Transaction build() {
        Transaction newTransaction = new Transaction();
        newTransaction.debitAccount = this.debitAccount;
        newTransaction.creditAccount = this.creditAccount;
        newTransaction.amount = this.amount;

        return newTransaction;
    }
}
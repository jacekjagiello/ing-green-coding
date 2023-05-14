package jacekjagiello.inggreencoding.entities.transactions;

public class Account implements Comparable<Account> {
    private final String account;
    private int debit;
    private int credit;
    private double balance;

    public Account(String account) {
        this.account = account;
    }

    public void applyDebit(double amount) {
        debit++;
        this.balance -= amount;
    }

    public void applyCredit(double amount) {
        credit++;
        this.balance += amount;
    }

    public String getAccount() {
        return account;
    }

    public int getDebit() {
        return debit;
    }

    public int getCredit() {
        return credit;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public int compareTo(Account otherAccount) {
        return account.compareTo(otherAccount.account);
    }
}

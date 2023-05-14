package jacekjagiello.inggreencoding.usecase;

import builders.TransactionBuilder;
import jacekjagiello.inggreencoding.entities.transactions.Account;
import jacekjagiello.inggreencoding.entities.transactions.Transaction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessTransactionsTest {
    @Test
    void run() {
        ProcessTransactions generateAccountSummaryReport = new ProcessTransactions();

        final Transaction[] transactions = {
            new TransactionBuilder().withDebitAccount("32309111922661937852684864").withCreditAccount("06105023389842834748547303").forAmount(10.90).build(),
            new TransactionBuilder().withDebitAccount("31074318698137062235845814").withCreditAccount("66105036543749403346524547").forAmount(200.90).build(),
            new TransactionBuilder().withDebitAccount("66105036543749403346524547").withCreditAccount("32309111922661937852684864").forAmount(50.10).build(),
        };

        List<Account> accounts = generateAccountSummaryReport.run(transactions);

        assertEquals(4, accounts.size());

        Account account1 = accounts.get(0);
        assertEquals("06105023389842834748547303", account1.getAccount());
        assertEquals(0, account1.getDebit());
        assertEquals(1, account1.getCredit());
        assertEquals(10.9,account1.getBalance());

        Account account2 = accounts.get(1);
        assertEquals("31074318698137062235845814", account2.getAccount());
        assertEquals(1, account2.getDebit());
        assertEquals(0, account2.getCredit());
        assertEquals(-200.9, account2.getBalance());

        Account account3 = accounts.get(2);
        assertEquals("32309111922661937852684864", account3.getAccount());
        assertEquals(1,account3.getDebit());
        assertEquals(1, account3.getCredit());
        assertEquals(39.2, account3.getBalance());

        Account account4 = accounts.get(3);
        assertEquals("66105036543749403346524547", account4.getAccount());
        assertEquals(1, account4.getDebit());
        assertEquals(1, account4.getCredit());
        assertEquals(150.8, account4.getBalance());
    }
}

package jacekjagiello.inggreencoding.entities.transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    @Test
    void canBeInstantiatedCorrectly() {
        Account account = new Account("12345");

        assertEquals("12345", account.getAccount());
        assertEquals(0, account.getCredit());
        assertEquals(0, account.getDebit());
        assertEquals(0, account.getBalance());
    }

    @Test
    void canPerformDebitAndCreditOperations() {
        Account account = new Account("12345");

        account.applyCredit(100);
        assertEquals(1, account.getCredit());
        assertEquals(0, account.getDebit());
        assertEquals(100, account.getBalance());

        account.applyDebit(50);
        assertEquals(1, account.getCredit());
        assertEquals(1, account.getDebit());
        assertEquals(50, account.getBalance());
    }
}

package jacekjagiello.inggreencoding.usecase;

import jacekjagiello.inggreencoding.entities.transactions.Account;
import jacekjagiello.inggreencoding.entities.transactions.Transaction;

import java.util.*;

public class ProcessTransactions {
    public List<Account> run(Transaction[] transactions)  {
        Map<String, Account> accounts = new HashMap<>();

        for (Transaction transaction : transactions) {
            double amount = transaction.amount;
            String debitAccountNumber = transaction.debitAccount;
            String creditAccountNumber = transaction.creditAccount;

            if (!accounts.containsKey(debitAccountNumber)) {
                accounts.put(debitAccountNumber, new Account(debitAccountNumber));
            }
            if (!accounts.containsKey(creditAccountNumber)) {
                accounts.put(creditAccountNumber, new Account(creditAccountNumber));
            }

            accounts.get(debitAccountNumber).applyDebit(amount);
            accounts.get(creditAccountNumber).applyCredit(amount);
        }

        List<Account> sortedAccounts = new ArrayList<>(accounts.values());
        Collections.sort(sortedAccounts);

        return sortedAccounts;
    }
}

package jacekjagiello.inggreencoding.usecase;

import builders.OrderBuilder;
import builders.TransactionBuilder;
import jacekjagiello.inggreencoding.entities.atmservice.Order;
import jacekjagiello.inggreencoding.entities.transactions.Transaction;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class ProcessTransactionsBenchmark {
    ProcessTransactions processTransactions = new ProcessTransactions();

    @State(Scope.Thread)
    public static class BenchmarkContext {
        @Param({ "1000", "5000", "25000", "100000" })
        public int numberOfTransactions;

        public Transaction[] transactions;

        @Setup
        public void initState() {
            List<Transaction> transactions = new ArrayList<>();

            for (int i = 0; i < this.numberOfTransactions; i++) {
                Transaction randomTransaction = new TransactionBuilder()
                        .withRandomCreditAccount()
                        .withRandomDebitAccount()
                        .forRandomAmount()
                        .build();
                transactions.add(randomTransaction);
            }

            this.transactions = transactions.toArray(Transaction[]::new);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 2)
    public void runBenchmark(BenchmarkContext context) {
        this.processTransactions.run(context.transactions);
    }
}
package jacekjagiello.inggreencoding.usecase;

import builders.OrderBuilder;
import jacekjagiello.inggreencoding.entities.atmservice.Order;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class CalculateAtmsOrderBenchmark  {
    CalculateAtmsOrder calculateAtmsOrder = new CalculateAtmsOrder();

    @State(Scope.Thread)
    public static class BenchmarkContext {
        @Param({ "1000", "5000", "25000", "100000" })
        public int numberOfOrders;

        public Order[] orders;

        @Setup
        public void initState() {
            List<Order> orders = new ArrayList<>();

            for (int i = 0; i < this.numberOfOrders; i++) {
                Order randomOrder = new OrderBuilder()
                    .withRandomRequestType()
                    .inRandomRegion()
                    .withRandomAtmId()
                    .build();
                orders.add(randomOrder);
            }

            this.orders = orders.toArray(Order[]::new);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 2)
    public void runBenchmark(BenchmarkContext context) {
        this.calculateAtmsOrder.run(context.orders);
    }
}
package jacekjagiello.inggreencoding.usecase;

import builders.ClanBuilder;
import builders.OrderBuilder;
import jacekjagiello.inggreencoding.entities.onlinegame.Clan;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class GenerateEventQueueBenchmark {
    GenerateEventQueue generateEventQueue = new GenerateEventQueue();

    @State(Scope.Thread)
    public static class BenchmarkContext {
        @Param({ "1000", "5000", "25000", "100000" })
        public int numberOfClans;

        public List<Clan> clans  = new ArrayList<>();

        @Setup
        public void initState() {
            for (int i = 0; i < this.numberOfClans; i++) {
                Clan randomClan = new ClanBuilder()
                    .withRandomNumberOfPlayer()
                    .withRandomPoints()
                    .build();
                clans.add(randomClan);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 2)
    public void runBenchmark(BenchmarkContext context) {
        this.generateEventQueue.run(6, context.clans);
    }
}
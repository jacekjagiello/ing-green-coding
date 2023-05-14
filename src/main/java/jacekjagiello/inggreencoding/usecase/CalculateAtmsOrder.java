package jacekjagiello.inggreencoding.usecase;

import jacekjagiello.inggreencoding.entities.atmservice.Order;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CalculateAtmsOrder {
    public Order[] run(Order[] orders) {
        return Arrays.stream(orders)
                .sorted()
                .filter(distinctByKeys(Order::getRegion, Order::getAtmId))
                .toArray(Order[]::new);
    }

    @SafeVarargs
    @NotNull
    @Contract(pure = true)
    private static Predicate<Order> distinctByKeys(final Function<? super Order, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }
}

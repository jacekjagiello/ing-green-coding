package jacekjagiello.inggreencoding.entities.atmservice;

import builders.OrderBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    @Test
    void canBeInstantiatedCorrectly() {
        Order order = new Order();
        order.setAtmId(1);
        order.setRegion(2);
        order.setRequestType(RequestType.SIGNAL_LOW);

        assertEquals(1, order.getAtmId());
        assertEquals(2, order.getRegion());
        assertEquals(RequestType.SIGNAL_LOW, order.getRequestType());
    }

    @Test
    void canBeSortedByRegionAndPriority() {
        Order order1 = new OrderBuilder().Standard().inRegion(4).withAtmId(1).build();
        Order order2 = new OrderBuilder().Priority().inRegion(3).withAtmId(2).build();
        Order order3 = new OrderBuilder().Standard().inRegion(3).withAtmId(3).build();
        Order order4 = new OrderBuilder().FailureRestart().inRegion(5).withAtmId(4).build();

        List<Order> orders = new ArrayList<>(List.of(new Order[]{order1, order2, order3, order4}));
        List<Order> expectedOrders = new ArrayList<>(List.of(new Order[]{order2, order3, order1, order4}));

        Collections.sort(orders);

        assertEquals(expectedOrders, orders);
    }
}

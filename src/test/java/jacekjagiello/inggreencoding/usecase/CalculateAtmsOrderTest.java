package jacekjagiello.inggreencoding.usecase;

import builders.OrderBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import jacekjagiello.inggreencoding.entities.atmservice.Order;
import static assertions.Assertions.assertThat;

class CalculateAtmsOrderTest {
    @Test
    void run() {
        CalculateAtmsOrder calculateAtmsOrdersUseCase = new CalculateAtmsOrder();

        Order order1 = new OrderBuilder().Standard().inRegion(4).withAtmId(1).build();
        Order order2 = new OrderBuilder().Standard().inRegion(1).withAtmId(1).build();
        Order order3 = new OrderBuilder().Standard().inRegion(2).withAtmId(1).build();
        Order order4 = new OrderBuilder().Priority().inRegion(3).withAtmId(2).build();
        Order order5 = new OrderBuilder().Standard().inRegion(3).withAtmId(1).build();
        Order order6 = new OrderBuilder().SignalLow().inRegion(2).withAtmId(1).build();
        Order order7 = new OrderBuilder().Standard().inRegion(5).withAtmId(2).build();
        Order order8 = new OrderBuilder().FailureRestart().inRegion(5).withAtmId(1).build();

        Order[] unsortedOrders = {order1, order2, order3, order4, order5, order6, order7, order8};
        Order[] expectedSortedOrders = {order2, order3, order4, order5, order1, order8, order7};

        Order[] sortedOrders = calculateAtmsOrdersUseCase.run(unsortedOrders);

        assertEquals(7, sortedOrders.length);

        for (int i = 0; i < sortedOrders.length; i++) {
            assertThat(sortedOrders[i]).equalsOrder(expectedSortedOrders[i]);
        }
    }
}
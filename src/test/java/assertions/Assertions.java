package assertions;

import jacekjagiello.inggreencoding.entities.atmservice.Order;

public class Assertions {
    public static OrderAssert assertThat(Order actual) {
        return new OrderAssert(actual);
    }
}
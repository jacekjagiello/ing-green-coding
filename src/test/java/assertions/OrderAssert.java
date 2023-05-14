package assertions;

import jacekjagiello.inggreencoding.entities.atmservice.Order;
import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

public class OrderAssert extends AbstractAssert<OrderAssert, Order> {
    public OrderAssert(Order actual) {
        super(actual, OrderAssert.class);
    }

    public static OrderAssert assertThat(Order actual) {
        return new OrderAssert(actual);
    }

    public OrderAssert equalsOrder(Order orderToCompare) {
        isNotNull();
        if (actual.getAtmId() != orderToCompare.getAtmId()) {
            failWithMessage("Expected Order to have atmId %s but was %s", actual.getAtmId(), orderToCompare.getAtmId());
        }
        if (actual.getRegion() != orderToCompare.getRegion()) {
            failWithMessage("Expected Order to have a region %s but was %s", actual.getRegion(), orderToCompare.getRegion());
        }
        if (!Objects.equals(actual.getRequestType(), orderToCompare.getRequestType())) {
            failWithMessage("Expected Order to have request type %s but was %s", actual.getRequestType(), orderToCompare.getRequestType());
        }
        return this;
    }
}
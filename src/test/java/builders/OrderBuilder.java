package builders;

import jacekjagiello.inggreencoding.entities.atmservice.Order;
import jacekjagiello.inggreencoding.entities.atmservice.RequestType;
import utils.RandomUtils;

public class OrderBuilder extends RandomUtils {
    private RequestType requestType;
    private int atmId;
    private int region;

    public OrderBuilder inRegion(int region) {
        this.region = region;
        return this;
    }

    public OrderBuilder Standard() {
        this.requestType = RequestType.STANDARD;
        return this;
    }

    public OrderBuilder Priority() {
        this.requestType = RequestType.PRIORITY;
        return this;
    }

    public OrderBuilder SignalLow() {
        this.requestType = RequestType.SIGNAL_LOW;
        return this;
    }

    public OrderBuilder FailureRestart() {
        this.requestType = RequestType.FAILURE_RESTART;
        return this;
    }

    public OrderBuilder withRandomAtmId() {
        this.atmId = this.randomNumber(1, 10);
        return this;
    }

    public OrderBuilder withRandomRequestType() {
        switch (this.randomNumber(0, 3)) {
            case 0 -> this.requestType = RequestType.PRIORITY;
            case 1 -> this.requestType = RequestType.FAILURE_RESTART;
            case 2 -> this.requestType = RequestType.SIGNAL_LOW;
            default -> this.requestType = RequestType.STANDARD;
        }
        return this;
    }

    public OrderBuilder inRandomRegion() {
        this.region = this.randomNumber(1, 10);
        return this;
    }

    public OrderBuilder withAtmId(int atmId) {
        this.atmId = atmId;
        return this;
    }

    public Order build() {
        Order newOrder = new Order();
        newOrder.setRegion(this.region);
        newOrder.setRequestType(this.requestType);
        newOrder.setAtmId(this.atmId);

        return newOrder;
    }
}
package jacekjagiello.inggreencoding.entities.atmservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

@JsonIgnoreProperties(value = {"requestType", "priority"}, allowSetters = true)
public class Order implements Comparable<Order> {
    private int region;
    private RequestType requestType;
    private int atmId;
    private final HashMap<RequestType, Integer> requestTypeToPriorityMap = new HashMap<>();

    public Order() {
        this.requestTypeToPriorityMap.put(RequestType.FAILURE_RESTART, 0);
        this.requestTypeToPriorityMap.put(RequestType.PRIORITY, 1);
        this.requestTypeToPriorityMap.put(RequestType.STANDARD, 2);
        this.requestTypeToPriorityMap.put(RequestType.SIGNAL_LOW, 3);
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    @Override
    public int compareTo(@NotNull Order orderB) {
        if (this.getRegion() < orderB.getRegion()) {
            return -1;
        } else if (this.getRegion() > orderB.getRegion()) {
            return 1;
        } else {
            if (this.getPriority() < orderB.getPriority()) {
                return -1;
            } else if (this.getPriority() > orderB.getPriority()) {
                return 1;
            } else {
                if (Objects.equals(this.getRequestType(), RequestType.PRIORITY)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }
    }

    private int getPriority() {
        return this.requestTypeToPriorityMap.get(this.requestType);
    }
}
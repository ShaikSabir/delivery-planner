package org.assignment.delivery.planner.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Agent extends User{
    GeoLocation location;
    DeliveryPlan deliveryPlan;
}

package org.assignment.delivery.planner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains additional metadata related to an order.
 */
@Getter
@Setter
@Builder
public class OrderDetails {

    /**
     * Total price of the order.
     */
    private final double price;
}

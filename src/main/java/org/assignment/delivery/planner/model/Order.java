package org.assignment.delivery.planner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a food delivery order.
 */
@Getter
@Setter
@Builder
public class Order {

    /**
     * Unique identifier for the order.
     */
    private String orderId;

    /**
     * Customer who placed the order.
     */
    private Customer customer;

    /**
     * Restaurant from which the order is to be picked up.
     */
    private Restaurant restaurant;

    /**
     * Optional additional details for the order, such as item list or delivery preferences.
     */
    @Nullable
    private OrderDetails details;
}

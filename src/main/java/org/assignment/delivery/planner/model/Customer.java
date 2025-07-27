package org.assignment.delivery.planner.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Represents a customer in the delivery system.
 * Inherits common user properties from {@link User}.
 */
@Getter
@Setter
@SuperBuilder
@ToString
public class Customer extends User {

    /**
     * List of order IDs associated with the customer.
     */
    private List<String> orderIds;

    /**
     * Name of the customer.
     */
    private final String name;

    /**
     * Delivery address of the customer.
     */
    private final Address address;
}

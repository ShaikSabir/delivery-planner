package org.assignment.delivery.planner.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Represents a restaurant involved in the delivery ecosystem.
 */
@Getter
@Setter
@SuperBuilder
@ToString
public class Restaurant extends User {

    /**
     * Name of the restaurant.
     */
    final String name;

    /**
     * Physical address of the restaurant.
     */
    final Address address;

    /**
     * Average food preparation time (in minutes or appropriate time unit) used to estimate ready time.
     */
    final double averagePreparationTime;

    /**
     * List of orders associated with this restaurant.
     */
    List<Order> orders;
}

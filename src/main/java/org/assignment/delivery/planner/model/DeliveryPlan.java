package org.assignment.delivery.planner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents the final delivery plan containing the users (e.g., restaurants and customers)
 * in the order they should be visited during the route.
 */
@Getter
@Setter
@Builder
public class DeliveryPlan {

    /**
     * Ordered list of users (either restaurants or customers) to be visited in the delivery route.
     */
    private final List<User> users;
}

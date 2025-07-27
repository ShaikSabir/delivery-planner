package org.assignment.delivery.planner.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Base class representing a generic user in the system.
 * Can be extended by specific user types like {@link Customer} or {@link Restaurant}.
 */
@Getter
@Setter
@SuperBuilder
public class User {

    /**
     * Unique identifier for the user (restaurant or customer).
     */
    private String id;
}

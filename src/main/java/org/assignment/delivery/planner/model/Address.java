package org.assignment.delivery.planner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a physical address in the delivery system.
 */
@Getter
@Setter
@Builder
public class Address {

    /**
     * The geographical coordinates (latitude and longitude) of the address.
     */
    private final GeoLocation location;
}

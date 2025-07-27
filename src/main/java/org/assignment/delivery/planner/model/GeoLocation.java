package org.assignment.delivery.planner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a geographical location using latitude and longitude coordinates.
 */
@Getter
@Setter
@Builder
@ToString
public class GeoLocation {

    /**
     * Longitude coordinate (horizontal position).
     */
    private final double longitude;

    /**
     * Latitude coordinate (vertical position).
     */
    private final double latitude;
}

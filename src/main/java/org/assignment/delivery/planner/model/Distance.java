package org.assignment.delivery.planner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a distance measurement between two geographical points.
 */
@Getter
@Setter
@Builder
public class Distance {

    /**
     * Distance in appropriate units (e.g., kilometers or meters).
     */
    private double distance;
}

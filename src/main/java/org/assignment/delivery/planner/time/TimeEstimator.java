package org.assignment.delivery.planner.time;

import org.assignment.delivery.planner.model.Distance;

/**
 * Abstract class representing a time estimator for travel,
 * based on a given distance.
 */
public abstract class TimeEstimator {

    /**
     * Estimates travel time in minutes based on the given distance.
     *
     * @param distance the distance between two locations
     * @return estimated travel time in minutes
     */
    public double estimateTimeInMinutes(Distance distance) {
        return estimateTimeInHours(distance) * 60;
    }

    /**
     * Estimates travel time in hours based on the given distance.
     * Subclasses must implement this method.
     *
     * @param distance the distance between two locations
     * @return estimated travel time in hours
     */
    public abstract double estimateTimeInHours(Distance distance);
}

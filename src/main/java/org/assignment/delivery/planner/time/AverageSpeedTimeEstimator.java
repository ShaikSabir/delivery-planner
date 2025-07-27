package org.assignment.delivery.planner.time;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.assignment.delivery.planner.model.Distance;

/**
 * A simple time estimator that calculates travel time based on a constant average speed.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AverageSpeedTimeEstimator extends TimeEstimator {

    /**
     * Average travel speed in kilometers per hour.
     */
    private final double averageSpeedKmPerHour;

    /**
     * Constructs an {@code AverageSpeedTimeEstimator} with the specified average speed.
     *
     * @param averageSpeedKmPerHour the constant speed used for estimating travel time (km/h)
     */
    public AverageSpeedTimeEstimator(double averageSpeedKmPerHour) {
        this.averageSpeedKmPerHour = averageSpeedKmPerHour;
    }

    /**
     * Estimates the travel time in hours for the given distance.
     *
     * @param distance the distance to travel
     * @return estimated time in hours
     */
    @Override
    public double estimateTimeInHours(Distance distance) {
        if(averageSpeedKmPerHour > 0) {
            return distance.getDistance() / averageSpeedKmPerHour;
        } else {
            throw new UnsupportedOperationException("invalid Average Speed passed.");
        }

    }
}

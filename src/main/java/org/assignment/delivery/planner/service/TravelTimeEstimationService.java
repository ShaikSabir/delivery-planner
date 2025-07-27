package org.assignment.delivery.planner.service;

import lombok.Data;
import org.assignment.delivery.planner.distance.DistanceCalculator;
import org.assignment.delivery.planner.model.Distance;
import org.assignment.delivery.planner.model.GeoLocation;
import org.assignment.delivery.planner.time.TimeEstimator;

import javax.inject.Inject;

/**
 * Service responsible for estimating travel time and distance between two geo-locations.
 * Delegates the computation to {@link DistanceCalculator} and {@link TimeEstimator}.
 */
@Data
public class TravelTimeEstimationService {
    private final DistanceCalculator distanceCalculator;
    private final TimeEstimator timeEstimator;

    /**
     * Constructs a {@code TravelTimeEstimationService} with the given distance and time calculators.
     *
     * @param distanceCalculator the distance calculator component
     * @param timeEstimator the time estimator component
     */
    @Inject
    public TravelTimeEstimationService(DistanceCalculator distanceCalculator, TimeEstimator timeEstimator) {
        this.distanceCalculator = distanceCalculator;
        this.timeEstimator = timeEstimator;
    }

    /**
     * Calculates the distance in kilometers between two geo-locations.
     *
     * @param from the source location
     * @param to the destination location
     * @return the computed {@link Distance}
     */
    public Distance getDistanceInKm(GeoLocation from, GeoLocation to) {
        return distanceCalculator.calculateDistance(from, to);
    }

    /**
     * Estimates the travel time in minutes between two geo-locations.
     *
     * @param from the source location
     * @param to the destination location
     * @return estimated time in minutes
     */
    public double estimateTime(GeoLocation from, GeoLocation to) {
        Distance distance = getDistanceInKm(from, to);
        return timeEstimator.estimateTimeInMinutes(distance);
    }
}

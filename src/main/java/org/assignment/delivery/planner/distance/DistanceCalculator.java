package org.assignment.delivery.planner.distance;

import org.assignment.delivery.planner.model.Distance;
import org.assignment.delivery.planner.model.GeoLocation;

public abstract class DistanceCalculator {
    abstract public Distance calculateDistance(final GeoLocation sourceLocation, final GeoLocation destinationLocation);
}

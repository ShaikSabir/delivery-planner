package org.assignment.delivery.planner.distance;

import org.assignment.delivery.planner.model.Distance;
import org.assignment.delivery.planner.model.GeoLocation;

import javax.inject.Inject;

import static org.assignment.delivery.planner.utils.MathUtil.*;

public class HaversineDistanceCalculator extends DistanceCalculator{

        @Inject
        public HaversineDistanceCalculator(){}

        public Distance calculateDistance(final GeoLocation sourceLocation, final GeoLocation destinationLocation) {

            double sourceLongitude = sourceLocation.getLongitude();
            double sourceLatitude = sourceLocation.getLatitude();

            double destinationLongitude = destinationLocation.getLongitude();
            double destinationLatitude = destinationLocation.getLatitude();

            double deltaLatitudeInRadians = toRadians(destinationLatitude - sourceLatitude);
            double deltaLongitudeInRadians = toRadians(destinationLongitude - sourceLongitude);

            final double sourceLatitudeInRadians = toRadians(sourceLatitude);
            final double destinationLatitudeInRadians = toRadians(destinationLatitude);

            final double haversineLatitude = computeHaversine(deltaLatitudeInRadians);
            final double haversineLongitude = computeHaversine(deltaLongitudeInRadians);


            double haversineFormulaComponent = haversineLatitude
                    + Math.cos(sourceLatitudeInRadians) * Math.cos(destinationLatitudeInRadians) * haversineLongitude;

            double angularDistance = 2 * Math.atan2(
                    Math.sqrt(haversineFormulaComponent),
                    Math.sqrt(1 - haversineFormulaComponent)
            );

            return Distance.builder()
                    .distance(angularDistance * EARTH_RADIUS_KM)
                    .build();
        }
}

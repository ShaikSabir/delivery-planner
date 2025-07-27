package org.assignment.delivery.planner.service;

import org.assignment.delivery.planner.distance.DistanceCalculator;
import org.assignment.delivery.planner.model.Distance;
import org.assignment.delivery.planner.model.GeoLocation;
import org.assignment.delivery.planner.time.TimeEstimator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TravelTimeEstimationServiceTest {

    private DistanceCalculator distanceCalculator;
    private TimeEstimator timeEstimator;
    private TravelTimeEstimationService service;

    @BeforeEach
    void setUp() {
        distanceCalculator = mock(DistanceCalculator.class);
        timeEstimator = mock(TimeEstimator.class);
        service = new TravelTimeEstimationService(distanceCalculator, timeEstimator);
    }

    @Test
    void testGetDistanceInKm_shouldReturnExpectedDistance() {
        GeoLocation from = GeoLocation.builder().latitude(12.0).longitude(77.0).build();
        GeoLocation to = GeoLocation.builder().latitude(12.5).longitude(77.5).build();

        Distance expectedDistance = Distance.builder().distance(10.0).build();

        when(distanceCalculator.calculateDistance(from, to)).thenReturn(expectedDistance);

        Distance result = service.getDistanceInKm(from, to);

        assertEquals(expectedDistance, result);
        verify(distanceCalculator).calculateDistance(from, to);
    }

    @Test
    void testEstimateTime_shouldReturnEstimatedTimeInMinutes() {
        GeoLocation from = GeoLocation.builder().latitude(12.0).longitude(77.0).build();
        GeoLocation to = GeoLocation.builder().latitude(12.5).longitude(77.5).build();
        Distance distance = Distance.builder().distance(10.0).build();

        when(distanceCalculator.calculateDistance(from, to)).thenReturn(distance);
        when(timeEstimator.estimateTimeInMinutes(distance)).thenReturn(20.0);

        double time = service.estimateTime(from, to);

        assertEquals(20.0, time);
        verify(distanceCalculator).calculateDistance(from, to);
        verify(timeEstimator).estimateTimeInMinutes(distance);
    }
}

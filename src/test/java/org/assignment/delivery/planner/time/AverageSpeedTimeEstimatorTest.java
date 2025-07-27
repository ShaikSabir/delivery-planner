package org.assignment.delivery.planner.time;

import org.assignment.delivery.planner.model.Distance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AverageSpeedTimeEstimatorTest {

    private AverageSpeedTimeEstimator estimator;

    @BeforeEach
    void setUp() {
        // 60 km/h average speed
        estimator = new AverageSpeedTimeEstimator(60.0);
    }

    @Test
    void estimateTimeInHours_shouldReturnCorrectTime_givenPositiveDistance() {
        Distance distance = Distance.builder().distance(120.0).build(); // 120 km

        double time = estimator.estimateTimeInHours(distance);

        assertEquals(2.0, time, 0.0001, "Expected 2 hours for 120 km at 60 km/h");
    }

    @Test
    void estimateTimeInMinutes_shouldReturnCorrectTime_givenPositiveDistance() {
        Distance distance = Distance.builder().distance(90.0).build(); // 90 km

        double minutes = estimator.estimateTimeInMinutes(distance);

        assertEquals(90.0, minutes, 0.0001, "Expected 90 minutes for 90 km at 60 km/h");
    }

    @Test
    void estimateTime_shouldReturnZero_givenZeroDistance() {
        Distance distance = Distance.builder().distance(0.0).build();

        assertEquals(0.0, estimator.estimateTimeInHours(distance), "Time in hours should be 0 for 0 distance");
        assertEquals(0.0, estimator.estimateTimeInMinutes(distance), "Time in minutes should be 0 for 0 distance");
    }

    @Test
    void estimateTime_shouldHandleFractionalSpeedAndDistance() {
        estimator = new AverageSpeedTimeEstimator(30.5); // km/h
        Distance distance = Distance.builder().distance(45.75).build(); // km

        double time = estimator.estimateTimeInHours(distance);
        assertEquals(1.5, time, 0.01, "Should estimate time correctly for fractional input");
    }

    @Test
    void estimateTime_shouldThrowException_givenZeroSpeed() {
        estimator = new AverageSpeedTimeEstimator(0.0);
        Distance distance = Distance.builder().distance(10.0).build();

        assertThrows(UnsupportedOperationException.class, () -> estimator.estimateTimeInHours(distance),
                "Should throw exception when dividing by zero speed");
    }
}

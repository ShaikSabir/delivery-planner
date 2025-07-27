package org.assignment.delivery.planner.distance;

import org.assignment.delivery.planner.model.Distance;
import org.assignment.delivery.planner.model.GeoLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HaversineDistanceCalculatorTest {

    private HaversineDistanceCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new HaversineDistanceCalculator();
    }

    @Test
    void testCalculateDistance_sameLocation_shouldReturnZero() {
        GeoLocation location = GeoLocation.builder().latitude(12.9716).longitude(77.5946).build();

        Distance result = calculator.calculateDistance(location, location);

        assertEquals(0.0, result.getDistance(), 0.0001, "Distance between same points should be zero");
    }

    @Test
    void testCalculateDistance_knownCities_shouldBeRoughlyCorrect() {
        // New York (40.7128° N, 74.0060° W)
        GeoLocation newYork = GeoLocation.builder().latitude(40.7128).longitude(-74.0060).build();

        // London (51.5074° N, 0.1278° W)
        GeoLocation london = GeoLocation.builder().latitude(51.5074).longitude(-0.1278).build();

        Distance result = calculator.calculateDistance(newYork, london);

        double expectedDistanceKm = 5567.0; // Approximate Haversine distance in km
        assertEquals(expectedDistanceKm, result.getDistance(), 20.0,
                "Distance between New York and London should be around 5567 km");
    }

    @Test
    void testCalculateDistance_shortDistance_shouldReturnSmallValue() {
        GeoLocation loc1 = GeoLocation.builder().latitude(12.9716).longitude(77.5946).build(); // Bangalore
        GeoLocation loc2 = GeoLocation.builder().latitude(12.9721).longitude(77.5950).build(); // ~50-100 meters apart

        Distance result = calculator.calculateDistance(loc1, loc2);

        assertTrue(result.getDistance() < 0.2, "Expected very small distance between close points");
    }

    @Test
    void testCalculateDistance_equatorToPole_shouldBeQuarterOfEarthCircumference() {
        GeoLocation equator = GeoLocation.builder().latitude(0).longitude(0).build();
        GeoLocation northPole = GeoLocation.builder().latitude(90).longitude(0).build();

        Distance result = calculator.calculateDistance(equator, northPole);

        double expectedDistance = Math.PI / 2 * 6371; // π/2 * Earth's radius in km
        assertEquals(expectedDistance, result.getDistance(), 1.0, "Expected ~10007 km from Equator to North Pole");
    }
}
